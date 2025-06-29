package com.artesansoul.artesansoulV3.Models.Produto.comportamental;

import com.artesansoul.artesansoulV3.Models.Cliente;

public interface ParticipacaoObserver {
    void notificar(Cliente cliente, Feirinha feirinha, String acao);
}
