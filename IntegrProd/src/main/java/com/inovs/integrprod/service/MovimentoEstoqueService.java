package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.MovimentoEstoque;
import java.time.LocalDate;
import java.util.List;

public interface MovimentoEstoqueService {

    MovimentoEstoque salvar(MovimentoEstoque movimento);

    MovimentoEstoque buscarPorId(Long id);

    List<MovimentoEstoque> listarTodos();

    List<MovimentoEstoque> buscarPorFilial(Long filialId);

    List<MovimentoEstoque> buscarPorProduto(Long produtoId);

    List<MovimentoEstoque> buscarPorTipo(String tipoMovimento);

    List<MovimentoEstoque> buscarPorPeriodo(LocalDate inicio, LocalDate fim);

    List<MovimentoEstoque> buscarPorDocumento(String documentoTipo, String documentoNumero);

    List<MovimentoEstoque> buscarPorLote(String lote);

    void deletar(Long id);

    MovimentoEstoque estornarMovimento(Long id, String usuario);

    byte[] gerarRelatorioMovimentos(LocalDate inicio, LocalDate fim);

    void validarMovimento(MovimentoEstoque movimento);
}
