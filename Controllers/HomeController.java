package com.artesansoul.artesansoulV3.Controllers;





import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.ParticipacaoFeirinha;
import com.artesansoul.artesansoulV3.Models.Produto.Produto;
import com.artesansoul.artesansoulV3.Models.Produto.comportamental.Feirinha;
import com.artesansoul.artesansoulV3.Models.Produto.estruturais.ProdutoCeramicaDecorator;
import com.artesansoul.artesansoulV3.Models.Produto.estruturais.ProdutoCeramicaView;
import com.artesansoul.artesansoulV3.Models.Produto.estruturais.ProdutoCrocheAdapter;
import com.artesansoul.artesansoulV3.Models.Produto.estruturais.ProdutoMadeiraAdapter;
import com.artesansoul.artesansoulV3.Repositorio.ParticipacaoFeirinhaRepository;
import com.artesansoul.artesansoulV3.Repositorio.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {


    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    ParticipacaoFeirinhaRepository participacaoFeirinhaRepository;


    @GetMapping("/")
    public String exibirProdutos(Model model) {
        List<Produto> todosProdutos = produtoRepository.findAll();

        List<ProdutoMadeiraAdapter> madeiras = new ArrayList<>();
        List<ProdutoCrocheAdapter> croches = new ArrayList<>();
        List<ProdutoCeramicaView> ceramicas = new ArrayList<>();

        for (Produto produto : todosProdutos) {
            String categoria = produto.getCategoria().getNome();

            if (categoria.equalsIgnoreCase("Madeira")) {
                madeiras.add(new ProdutoMadeiraAdapter(produto));

            } else if (categoria.equalsIgnoreCase("Crochê")) {
                croches.add(new ProdutoCrocheAdapter(produto));

            } else if (categoria.equalsIgnoreCase("Ceramica")) {
                // Cast para acessar campos de cerâmica
                Produto produtoBase = produto;

                if (produto instanceof Produto) {
                    // Dados extras (mock ou do banco)
                    String tipoArgila = "Argila Vermelha"; // você pode pegar do banco se quiser
                    String tecnica = "Torno";

                    ProdutoCeramicaDecorator decorado = new ProdutoCeramicaDecorator(produtoBase, tipoArgila, tecnica);
                    ceramicas.add(new ProdutoCeramicaView(decorado));
                }
            }
        }

        model.addAttribute("madeiras", madeiras);
        model.addAttribute("croches", croches);
        model.addAttribute("ceramicas", ceramicas);

        return "index";
    }

    @GetMapping("/visualizarProduto/{id}")

    public String visualizarProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id).orElseThrow();

        Cliente cliente = produto.getCliente();  // ou outra forma de pegar o cliente


        List<ParticipacaoFeirinha> participacoes = participacaoFeirinhaRepository.findByClienteId(cliente.getId());

        List<Feirinha> feirinhasConfirmadas = participacoes.stream()
                .map(ParticipacaoFeirinha::getFeirinha)
                .toList();


        model.addAttribute("produto", produto);
        model.addAttribute("cliente", cliente);
        model.addAttribute("feirinhas", feirinhasConfirmadas);


        return "visualizarProduto";

    }


}