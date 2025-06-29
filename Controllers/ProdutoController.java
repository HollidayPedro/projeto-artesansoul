package com.artesansoul.artesansoulV3.Controllers;


import com.artesansoul.artesansoulV3.Models.Categoria;
import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Ceramica.ProdutoCeramica;
import com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Croche.ProdutoCroche;
import com.artesansoul.artesansoulV3.Models.Produto.Criacionais.ProdutoFactory;
import com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Madeira.ProdutoMadeira;
import com.artesansoul.artesansoulV3.Models.Produto.Produto;
import com.artesansoul.artesansoulV3.Repositorio.CategoriaRepository;
import com.artesansoul.artesansoulV3.Repositorio.ProdutoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {


    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/acoesProduto")
    public String acoesProduto() {

        return "gerenciarProdutos";

    }


    @GetMapping("/escolherCategoria")
    public String formProduto(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "escolherCategoria";

    }


    @GetMapping("/formMadeira")
    public String formMadeira(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formMadeira";
    }

    @GetMapping("/formCeramica")
    public String formCeramica(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formCeramica";
    }

    @GetMapping("/formCroche")
    public String formCroche(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formCroche";
    }

    @PostMapping("/adicionarMadeira")
    public String adicionarProdutoMadeira(@RequestParam("nome") String nome,
                                          @RequestParam("descricao") String descricao,
                                          @RequestParam("preco") Double preco,
                                          @RequestParam("quantidade") Integer quantidade,
                                          @RequestParam("tipoMadeira") String tipoMadeira,
                                          @RequestParam("acabamento") String acabamento,
                                          @RequestParam("categoria_id") Long categoriaId,
                                          @RequestParam("imagem") MultipartFile imagem,
                                          HttpSession session,
                                          Model model) {

        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado == null) {
            model.addAttribute("erro", "Você precisa estar logado para adicionar um produto.");
            return "redirect:/clientes/login";
        }

        ProdutoMadeira produto = (ProdutoMadeira) ProdutoFactory.criarProdutoMadeira(nome, descricao, preco, quantidade, tipoMadeira, acabamento);
        produto.setCliente(clienteLogado);

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        produto.setCategoria(categoria);

        try {
            if (imagem != null && !imagem.isEmpty()) {
                String diretorioUsuario = "uploads/" + clienteLogado.getEmail() + "/produtos/";
                Files.createDirectories(Paths.get(diretorioUsuario));

                String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
                Path caminho = Paths.get(diretorioUsuario + nomeImagem);

                Files.write(caminho, imagem.getBytes());

                produto.setImagem("/" + diretorioUsuario + nomeImagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao fazer upload da imagem do produto.");
            return "redirect:/produto/formProdutoMadeira";
        }

        produtoRepository.save(produto);

        return "redirect:/produto/acoesProduto";
    }


    @PostMapping("/adicionarCeramica")
    public String adicionarProdutoCeramica(@RequestParam("nome") String nome,
                                           @RequestParam("descricao") String descricao,
                                           @RequestParam("preco") Double preco,
                                           @RequestParam("quantidade") Integer quantidade,
                                           @RequestParam("tipoArgila") String tipoArgila,
                                           @RequestParam("tecnica") String tecnica,
                                           @RequestParam("categoria_id") Long categoriaId,
                                           @RequestParam("imagem") MultipartFile imagem,
                                           HttpSession session,
                                           Model model) {

        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado == null) {
            model.addAttribute("erro", "Você precisa estar logado para adicionar um produto.");
            return "redirect:/clientes/login";
        }

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        String caminhoImagem = null;

        try {
            if (imagem != null && !imagem.isEmpty()) {
                String diretorioUsuario = "uploads/" + clienteLogado.getEmail() + "/produtos/";
                Files.createDirectories(Paths.get(diretorioUsuario));

                String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
                Path caminho = Paths.get(diretorioUsuario + nomeImagem);

                Files.write(caminho, imagem.getBytes());

                caminhoImagem = "/" + diretorioUsuario + nomeImagem;
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao fazer upload da imagem do produto.");
            return "redirect:/produto/formProdutoCeramica";
        }

        ProdutoCeramica produto = new ProdutoCeramica.Builder()
                .setNome(nome)
                .setDescricao(descricao)
                .setPreco(preco)
                .setQuantidade(quantidade)
                .setTipoArgila(tipoArgila)
                .setTecnica(tecnica)
                .setCategoria(categoria)
                .setCliente(clienteLogado)
                .setImagem(caminhoImagem)
                .build();

        produtoRepository.save(produto);

        return "redirect:/produto/acoesProduto";
    }

    @PostMapping("/adicionarCroche")
    public String adicionarProdutoCroche(@RequestParam("nome") String nome,
                                         @RequestParam("descricao") String descricao,
                                         @RequestParam("preco") Double preco,
                                         @RequestParam("quantidade") Integer quantidade,
                                         @RequestParam("tipoLinha") String tipoLinha,
                                         @RequestParam("tamanho") String tamanho,
                                         @RequestParam("categoria_id") Long categoriaId,
                                         @RequestParam("imagem") MultipartFile imagem,
                                         HttpSession session,
                                         Model model) {

        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado == null) {
            model.addAttribute("erro", "Você precisa estar logado para adicionar um produto.");
            return "redirect:/clientes/login";
        }

        ProdutoCroche produto = (ProdutoCroche) ProdutoFactory.criarProdutoCroche(nome, descricao, preco, quantidade, tipoLinha, tamanho);
        produto.setCliente(clienteLogado);

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        produto.setCategoria(categoria);

        try {
            if (imagem != null && !imagem.isEmpty()) {
                String diretorioUsuario = "uploads/" + clienteLogado.getEmail() + "/produtos/";
                Files.createDirectories(Paths.get(diretorioUsuario));

                String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
                Path caminho = Paths.get(diretorioUsuario + nomeImagem);

                Files.write(caminho, imagem.getBytes());

                produto.setImagem("/" + diretorioUsuario + nomeImagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao fazer upload da imagem do produto.");
            return "redirect:/produto/formProdutoCroche";
        }

        produtoRepository.save(produto);

        return "redirect:/produto/acoesProduto";
    }



    @GetMapping("/editarProduto")
    public String listarMeusProdutos(HttpSession session, Model model) {
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");

        if (clienteLogado == null) {
            model.addAttribute("erro", "Você precisa estar logado para ver seus produtos.");
            return "redirect:/clientes/login";
        }




        // Busca todos os produtos desse cliente
        List<Produto> meusProdutos = produtoRepository.findByCliente(clienteLogado);

        model.addAttribute("produtos", meusProdutos);

        return "editarProduto";  // Nome da view (HTML) que vai exibir a lista
    }



    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable("id") int id, Model model, HttpSession session) {

        Produto produto = produtoRepository.findById((long) id)
                .orElseThrow(() -> new IllegalArgumentException("Produto inválido: " + id));

        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaRepository.findAll());

        return "formEditarProduto";  // Pode usar o mesmo form, só verificar se preenche os values!
    }


    @PostMapping("/SalvarEdicao/{id}")
    public String editarProduto(@PathVariable("id") Long id,
                                @RequestParam("nome") String nome,
                                @RequestParam("descricao") String descricao,
                                @RequestParam("preco") Double preco,
                                @RequestParam("quantidade") Integer quantidade,
                                @RequestParam("categoria_id") Long categoriaId,
                                @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                                HttpSession session,
                                Model model) {

        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
        if (clienteLogado == null) {
            model.addAttribute("erro", "Você precisa estar logado para editar um produto.");
            return "redirect:/clientes/login";
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao cliente logado
        if (!produto.getCliente().getId().equals(clienteLogado.getId())) {
            model.addAttribute("erro", "Você não tem permissão para editar este produto.");
            return "redirect:/produto/editarProduto";
        }

        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setEstoque(quantidade); // Se o campo for 'quantidade', altere aqui para setQuantidade

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        produto.setCategoria(categoria);

        try {
            if (imagem != null && !imagem.isEmpty()) {
                String diretorioUsuario = "uploads/" + clienteLogado.getEmail() + "/produtos/";
                Files.createDirectories(Paths.get(diretorioUsuario));

                String nomeImagem = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
                Path caminho = Paths.get(diretorioUsuario + nomeImagem);

                Files.write(caminho, imagem.getBytes());

                // Opcional: excluir a imagem anterior se quiser
                // deleteImagem(produto.getImagem());

                produto.setImagem("/" + diretorioUsuario + nomeImagem.replace("\\", "/"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao fazer upload da imagem do produto.");
            return "redirect:/produto/acoesProduto";
        }

        produtoRepository.save(produto);

        return "redirect:/produto/editarProduto";
    }




    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable("id") Long id, HttpSession session, Model model) {
        Cliente clienteLogado = (Cliente) session.getAttribute("clienteLogado");
        if (clienteLogado == null) {
            model.addAttribute("erro", "Você precisa estar logado para excluir um produto.");
            return "redirect:/clientes/login";
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao cliente logado
        if (!produto.getCliente().getId().equals(clienteLogado.getId())) {
            model.addAttribute("erro", "Você não tem permissão para excluir este produto.");
            return "redirect:/produto/acoesProduto";
        }

        // Opcional: Excluir a imagem física do servidor
        try {
            String caminhoImagem = produto.getImagem();
            if (caminhoImagem != null) {
                Path caminho = Paths.get(caminhoImagem.replaceFirst("/", ""));
                Files.deleteIfExists(caminho);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        produtoRepository.delete(produto);

        model.addAttribute("mensagem", "Produto excluído com sucesso.");
        return "redirect:/produto/editarProduto";
    }


}


