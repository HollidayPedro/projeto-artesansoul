package com.artesansoul.artesansoulV3.Models.Produto;

import com.artesansoul.artesansoulV3.Models.Categoria;
import com.artesansoul.artesansoulV3.Models.Cliente;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_produto", discriminatorType = DiscriminatorType.STRING)
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long idProduto;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private Double preco;

    private Integer quantidade;

    private Integer estoque;

    private String imagem;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
