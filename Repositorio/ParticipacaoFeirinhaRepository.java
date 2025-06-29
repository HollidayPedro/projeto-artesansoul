package com.artesansoul.artesansoulV3.Repositorio;


import com.artesansoul.artesansoulV3.Models.ParticipacaoFeirinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipacaoFeirinhaRepository extends JpaRepository<ParticipacaoFeirinha, Long> {

    List<ParticipacaoFeirinha> findByClienteId(Long clienteId);
    Optional<ParticipacaoFeirinha> findByClienteIdAndFeirinhaId(Long clienteId, Long feirinhaId);
}