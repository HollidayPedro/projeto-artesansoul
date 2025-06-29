package com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Ceramica;

import com.artesansoul.artesansoulV3.Models.Categoria;
import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.Produto.Produto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("Ceramica")
public class ProdutoCeramica extends Produto {
    private String tipoArgila;
    private String tecnica;

    protected ProdutoCeramica() {
    }


    private ProdutoCeramica(Builder builder) {
        this.setNome(builder.nome);
        this.setDescricao(builder.descricao);
        this.setPreco(builder.preco);
        this.setQuantidade(builder.quantidade);
        this.setEstoque(builder.quantidade);
        this.setImagem(builder.imagem);
        this.setCategoria(builder.categoria);
        this.setCliente(builder.cliente);

        this.tipoArgila = builder.tipoArgila;
        this.tecnica = builder.tecnica;
    }

    // Getters e Setters adicionais, se quiser expor tipoArgila e tecnica
    public String getTipoArgila() {
        return tipoArgila;
    }

    public String getTecnica() {
        return tecnica;
    }

    public static class Builder {
        private String nome;
        private String descricao;
        private Double preco;
        private Integer quantidade;
        private String imagem;
        private Categoria categoria;
        private Cliente cliente;

        private String tipoArgila;
        private String tecnica;

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setDescricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder setPreco(Double preco) {
            this.preco = preco;
            return this;
        }

        public Builder setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public Builder setImagem(String imagem) {
            this.imagem = imagem;
            return this;
        }

        public Builder setCategoria(Categoria categoria) {
            this.categoria = categoria;
            return this;
        }

        public Builder setCliente(Cliente cliente) {
            this.cliente = cliente;
            return this;
        }

        public Builder setTipoArgila(String tipoArgila) {
            this.tipoArgila = tipoArgila;
            return this;
        }

        public Builder setTecnica(String tecnica) {
            this.tecnica = tecnica;
            return this;
        }

        public ProdutoCeramica build() {
            return new ProdutoCeramica(this);
        }
    }

}