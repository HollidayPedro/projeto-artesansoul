package com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Madeira;

import com.artesansoul.artesansoulV3.Models.Produto.Produto;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Madeira")
public class ProdutoMadeira extends Produto {

    private String tipoMadeira;
    private String acabamento;
}
