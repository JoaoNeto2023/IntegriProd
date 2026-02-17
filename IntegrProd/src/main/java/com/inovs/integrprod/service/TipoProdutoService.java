package com.inovs.integrprod.service;


import com.inovs.integrprod.model.entity.TipoProduto;
import java.util.List;

public interface TipoProdutoService {

    TipoProduto salvar(TipoProduto tipoProduto);

    TipoProduto buscarPorId(Long id);

    TipoProduto buscarPorCodigo(String codigo);

    List<TipoProduto> listarTodos();

    List<TipoProduto> buscarPorNatureza(String natureza);

    void deletar(Long id);

    TipoProduto atualizar(Long id, TipoProduto tipoProduto);
}
