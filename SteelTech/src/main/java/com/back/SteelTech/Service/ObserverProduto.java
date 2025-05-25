package com.back.SteelTech.Service;

import com.back.SteelTech.Entity.Produto;

public interface ObserverProduto {
    void notificacaoProdutoAlterado(Produto produtoAntes, Produto produtoDepois);
}
