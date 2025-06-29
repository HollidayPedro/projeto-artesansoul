package com.artesansoul.artesansoulV3.Models.Produto.comportamental;


import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Repositorio.ParticipacaoFeirinhaRepository;

public class RetirarParticipacaoCommand implements ParticipacaoCommand {

    private ParticipacaoFeirinhaRepository participacaoRepo;
    private Cliente cliente;
    private Long feirinhaId;

    public RetirarParticipacaoCommand(ParticipacaoFeirinhaRepository participacaoRepo, Cliente cliente, Long feirinhaId) {
        this.participacaoRepo = participacaoRepo;
        this.cliente = cliente;
        this.feirinhaId = feirinhaId;
    }

    @Override
    public void executar() {
        participacaoRepo.findByClienteIdAndFeirinhaId(cliente.getId(), feirinhaId)
                .ifPresent(participacaoRepo::delete);
    }
}
