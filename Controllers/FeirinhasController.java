package com.artesansoul.artesansoulV3.Controllers;

import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.ParticipacaoFeirinha;
import com.artesansoul.artesansoulV3.Models.Produto.comportamental.EmailParticipacaoObserver;
import com.artesansoul.artesansoulV3.Models.Produto.comportamental.Feirinha;
import com.artesansoul.artesansoulV3.Models.Produto.comportamental.ParticipacaoNotifier;
import com.artesansoul.artesansoulV3.Repositorio.ClienteRepository;
import com.artesansoul.artesansoulV3.Repositorio.FeirinhaRepository;
import com.artesansoul.artesansoulV3.Repositorio.ParticipacaoFeirinhaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/feirinhas")
public class FeirinhasController {

    @Autowired
    FeirinhaRepository feirinhaRepository;

    @Autowired
    ParticipacaoFeirinhaRepository participacaoFeirinhaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EmailParticipacaoObserver emailObserver;

    @Autowired
    ParticipacaoNotifier notifier;

    @GetMapping("/visualizarFeirinhas")
    public String listarFeirinhas(Model model, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        Long clienteId = cliente != null ? cliente.getId() : null;

        List<Feirinha> feirinhas = feirinhaRepository.findAll();

        for (Feirinha f : feirinhas) {
            if (clienteId != null) {
                boolean confirmado = participacaoFeirinhaRepository
                        .findByClienteIdAndFeirinhaId(clienteId, f.getId())
                        .isPresent();
                f.setConfirmado(confirmado);
            } else {
                f.setConfirmado(false);
            }
        }

        model.addAttribute("feirinhas", feirinhas);
        return "feirinhas";
    }

    @PostMapping("/confirmar/{id}")
    public String confirmarParticipacao(@PathVariable Long id, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        if (cliente != null) {
            Feirinha feirinha = feirinhaRepository.findById(id).orElseThrow();
            boolean jaConfirmado = participacaoFeirinhaRepository
                    .findByClienteIdAndFeirinhaId(cliente.getId(), feirinha.getId())
                    .isPresent();
            if (!jaConfirmado) {
                ParticipacaoFeirinha participacao = new ParticipacaoFeirinha();
                participacao.setCliente(cliente);
                participacao.setFeirinha(feirinha);
                participacaoFeirinhaRepository.save(participacao);

                notifier.adicionarObserver(emailObserver);
                notifier.notificarTodos(cliente, feirinha, "confirmar");
            }
        }
        return "redirect:/feirinhas/visualizarFeirinhas";
    }

    @PostMapping("/retirar/{id}")
    public String retirarParticipacao(@PathVariable Long id, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        if (cliente != null) {
            participacaoFeirinhaRepository.findByClienteIdAndFeirinhaId(cliente.getId(), id)
                    .ifPresent(p -> {
                        participacaoFeirinhaRepository.delete(p);
                        Feirinha feirinha = p.getFeirinha();
                        notifier.adicionarObserver(emailObserver);
                        notifier.notificarTodos(cliente, feirinha, "retirar");
                    });
        }
        return "redirect:/feirinhas/visualizarFeirinhas";
    }
}
