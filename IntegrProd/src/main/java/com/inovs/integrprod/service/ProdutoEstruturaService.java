package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.ProdutoEstrutura;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ProdutoEstruturaService {

    ProdutoEstrutura salvar(ProdutoEstrutura estrutura);

    ProdutoEstrutura buscarPorId(Long id);

    List<ProdutoEstrutura> listarPorProdutoEversao(Long produtoId, String versao);

    List<ProdutoEstrutura> listarEstruturaVigente(Long produtoId, String versao, LocalDate data);

    List<String> listarVersoes(Long produtoId);

    List<ProdutoEstrutura> listarOndeProdutoEComponente(Long produtoId);

    void deletar(Long id);

    ProdutoEstrutura atualizar(Long id, ProdutoEstrutura estrutura);

    void copiarVersao(Long produtoId, String versaoOrigem, String versaoDestino, String usuario);

    Map<String, Object> calcularCustoEstrutura(Long produtoId, String versao);

    void validarEstrutura(ProdutoEstrutura estrutura);
}