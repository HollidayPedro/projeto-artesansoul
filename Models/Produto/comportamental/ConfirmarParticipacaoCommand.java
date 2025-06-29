package com.artesansoul.artesansoulV3.Models.Produto.comportamental;



import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.ParticipacaoFeirinha;

import com.artesansoul.artesansoulV3.Repositorio.ParticipacaoFeirinhaRepository;

public class ConfirmarParticipacaoCommand implements ParticipacaoCommand {

    private ParticipacaoFeirinhaRepository participacaoRepo;
    private Cliente cliente;
    private Feirinha feirinha;

    public ConfirmarParticipacaoCommand(ParticipacaoFeirinhaRepository participacaoRepo, Cliente cliente, Feirinha feirinha) {
        this.participacaoRepo = participacaoRepo;
        this.cliente = cliente;
        this.feirinha = feirinha;
    }

    @Override
    public void executar() {
        boolean jaConfirmado = participacaoRepo.findByClienteIdAndFeirinhaId(cliente.getId(), feirinha.getId()).isPresent();
        if (!jaConfirmado) {
            ParticipacaoFeirinha participacao = new ParticipacaoFeirinha();
            participacao.setCliente(cliente);
            participacao.setFeirinha(feirinha);
            participacaoRepo.save(participacao);
        }
    }
}

