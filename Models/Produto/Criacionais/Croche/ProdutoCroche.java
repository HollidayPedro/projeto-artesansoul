package com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Croche;

import com.artesansoul.artesansoulV3.Models.Produto.Produto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Croche")
public class ProdutoCroche extends Produto {

    private String tipoLinha;
    private String tamanho;
}
