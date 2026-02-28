package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.ApontamentoProducao;
import java.time.LocalDate;
import java.util.List;

public interface ApontamentoProducaoService {

    ApontamentoProducao salvar(ApontamentoProducao apontamento);

    ApontamentoProducao buscarPorId(Long id);

    List<ApontamentoProducao> listarTodos();

    List<ApontamentoProducao> buscarPorOrdem(Long ordemId);

    List<ApontamentoProducao> buscarPorPostoTrabalho(Long postoTrabalhoId);

    List<ApontamentoProducao> buscarPorData(LocalDate data);

    List<ApontamentoProducao> buscarPorPeriodo(LocalDate inicio, LocalDate fim);

    void deletar(Long id);

    ApontamentoProducao atualizar(Long id, ApontamentoProducao apontamento);

    void validarApontamento(ApontamentoProducao apontamento);
}