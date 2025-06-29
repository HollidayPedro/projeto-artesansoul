package com.artesansoul.artesansoulV3.Models.Produto.estruturais;

import com.artesansoul.artesansoulV3.Models.Produto.Produto;

public class ProdutoCeramicaView {
    private ProdutoCeramicaDecorator decorator;

    public ProdutoCeramicaView(ProdutoCeramicaDecorator decorator) {
        this.decorator = decorator;
    }

    public Produto getProduto() {
        return decorator;
    }

    public String getDescricaoCeramica() {
        return "Produto em Cerâmica: " + decorator.getNome()
                + " | Técnica: " + decorator.getTecnica()
                + " | Tipo de Argila: " + decorator.getTipoArgila();
    }
}
