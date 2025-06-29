package com.artesansoul.artesansoulV3.Models;


import com.artesansoul.artesansoulV3.Models.Produto.comportamental.Feirinha;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ParticipacaoFeirinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Feirinha feirinha;
}
