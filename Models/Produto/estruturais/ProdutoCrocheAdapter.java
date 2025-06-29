package com.artesansoul.artesansoulV3.Models.Produto.estruturais;

import com.artesansoul.artesansoulV3.Models.Produto.Produto;

public class ProdutoCrocheAdapter {
    private Produto produto;

    public ProdutoCrocheAdapter(Produto produto) {
        this.produto = produto;
    }

    public String getDescricaoCroche() {
        return "Produto em Crochê: " + produto.getNome();
    }

    public Produto getProduto() {
        return produto;
    }
}
