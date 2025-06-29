package com.artesansoul.artesansoulV3.Models.Produto.comportamental;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Feirinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String localizacao;

    private String horario;

    private LocalDate dataEvento;

    private String imagemUrl;

    private String linkMapa;


    @Transient
    private boolean confirmado;


    public String getDataFormatada() {
        if (dataEvento != null) {
            return dataEvento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            return "";
        }
    }

}
