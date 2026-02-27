package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.RoteiroProducao;
import java.util.List;
import java.util.Map;

public interface RoteiroProducaoService {

    RoteiroProducao salvar(RoteiroProducao roteiro);

    RoteiroProducao buscarPorId(Long id);

    List<RoteiroProducao> listarPorProdutoEversao(Long produtoId, String versao);

    List<String> listarVersoes(Long produtoId);

    void deletar(Long id);

    RoteiroProducao atualizar(Long id, RoteiroProducao roteiro);

    void copiarVersao(Long produtoId, String versaoOrigem, String versaoDestino);

    Map<String, Object> calcularTempoTotal(Long produtoId, String versao);

    void validarRoteiro(RoteiroProducao roteiro);
}