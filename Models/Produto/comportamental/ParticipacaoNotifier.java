package com.artesansoul.artesansoulV3.Models.Produto.comportamental;

import com.artesansoul.artesansoulV3.Models.Cliente;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParticipacaoNotifier {

    private final List<ParticipacaoObserver> observers = new ArrayList<>();

    public void adicionarObserver(ParticipacaoObserver observer) {
        observers.add(observer);
    }

    public void notificarTodos(Cliente cliente, Feirinha feirinha, String acao) {
        for (ParticipacaoObserver o : observers) {
            o.notificar(cliente, feirinha, acao);
        }
    }
}

