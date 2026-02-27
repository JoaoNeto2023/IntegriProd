package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.Produto;
import java.math.BigDecimal;
import java.util.List;

public interface ProdutoService {

    Produto salvar(Produto produto);

    Produto buscarPorId(Long id);

    Produto buscarPorCodigo(String codigo);

    List<Produto> listarTodos();

    List<Produto> listarAtivos();

    List<Produto> buscarPorNome(String nome);

    List<Produto> buscarPorTipoProduto(Long tipoProdutoId);

    List<Produto> buscarPorFamilia(Long familiaId);

    List<Produto> buscarPorPrecoEntre(BigDecimal min, BigDecimal max);

    void deletar(Long id);

    Produto atualizar(Long id, Produto produto);

    Produto atualizarPreco(Long id, BigDecimal novoPreco);

    Produto atualizarStatus(Long id, String status);

    void validarProduto(Produto produto);
}