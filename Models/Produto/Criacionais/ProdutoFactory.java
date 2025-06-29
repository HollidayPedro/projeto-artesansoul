package com.artesansoul.artesansoulV3.Models.Produto.Criacionais;

import com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Croche.ProdutoCroche;
import com.artesansoul.artesansoulV3.Models.Produto.Criacionais.Madeira.ProdutoMadeira;
import com.artesansoul.artesansoulV3.Models.Produto.Produto;

public class ProdutoFactory {





    public static Produto criarProdutoMadeira(String nome, String descricao, Double preco, Integer quantidade,
                                              String tipoMadeira, String acabamento) {
        ProdutoMadeira produto = new ProdutoMadeira();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidade(quantidade);
        produto.setEstoque(quantidade);
        produto.setTipoMadeira(tipoMadeira);
        produto.setAcabamento(acabamento);
        return produto;
    }





    public static Produto criarProdutoCroche(String nome, String descricao, Double preco, Integer quantidade,
                                             String tipoLinha, String tamanho) {
        ProdutoCroche produto = new ProdutoCroche();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidade(quantidade);
        produto.setEstoque(quantidade);
        produto.setTipoLinha(tipoLinha);
        produto.setTamanho(tamanho);
        return produto;
    }

}
