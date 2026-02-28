package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.OrdemProducao;
import java.time.LocalDate;
import java.util.List;

public interface OrdemProducaoService {

    OrdemProducao salvar(OrdemProducao ordemProducao);

    OrdemProducao buscarPorId(Long id);

    List<OrdemProducao> listarTodas();

    List<OrdemProducao> buscarPorFilial(Long filialId);

    List<OrdemProducao> buscarPorProduto(Long produtoId);

    List<OrdemProducao> buscarPorStatus(String status);

    List<OrdemProducao> buscarPorPeriodo(LocalDate inicio, LocalDate fim);

    List<OrdemProducao> buscarAbertas();

    List<OrdemProducao> buscarAtrasadas();

    void deletar(Long id);

    OrdemProducao atualizar(Long id, OrdemProducao ordemProducao);

    void iniciar(Long id);

    void pausar(Long id);

    void concluir(Long id);

    void cancelar(Long id);

    void validarOrdem(OrdemProducao ordemProducao);
}