package com.artesansoul.artesansoulV3.Models.Produto.estruturais;



import com.artesansoul.artesansoulV3.Models.Categoria;
import com.artesansoul.artesansoulV3.Models.Cliente;
import com.artesansoul.artesansoulV3.Models.Produto.Produto;

public class ProdutoCeramicaDecorator extends Produto {
    private Produto produtoDecorado;
    private String tipoArgila;
    private String tecnica;

    public ProdutoCeramicaDecorator(Produto produtoDecorado, String tipoArgila, String tecnica) {
        this.produtoDecorado = produtoDecorado;
        this.tipoArgila = tipoArgila;
        this.tecnica = tecnica;
    }

    @Override
    public String getNome() {
        return produtoDecorado.getNome() + " (Cerâmica)";
    }

    @Override
    public String getDescricao() {
        return produtoDecorado.getDescricao() +
                "\nTipo de Argila: " + tipoArgila +
                "\nTécnica: " + tecnica;
    }

    @Override
    public Double getPreco() {
        return produtoDecorado.getPreco();
    }

    @Override
    public Integer getQuantidade() {
        return produtoDecorado.getQuantidade();
    }

    @Override
    public Integer getEstoque() {
        return produtoDecorado.getEstoque();
    }

    @Override
    public String getImagem() {
        return produtoDecorado.getImagem();
    }

    @Override
    public Categoria getCategoria() {
        return produtoDecorado.getCategoria();
    }

    @Override
    public Cliente getCliente() {
        return produtoDecorado.getCliente();
    }

    // Métodos adicionais específicos do Decorator
    public String getTipoArgila() {
        return tipoArgila;
    }

    public String getTecnica() {
        return tecnica;
    }
}
