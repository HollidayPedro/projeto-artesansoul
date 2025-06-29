package com.artesansoul.artesansoulV3.Models.Produto.estruturais;

import com.artesansoul.artesansoulV3.Models.Produto.Produto;

public class ProdutoMadeiraAdapter {
    private Produto produto;

    public ProdutoMadeiraAdapter(Produto produto) {
        this.produto = produto;
    }

    public String getDescricaoMadeira() {
        return "Produto em Madeira: " + produto.getNome();
    }

    public Produto getProduto() {
        return produto;
    }
}
