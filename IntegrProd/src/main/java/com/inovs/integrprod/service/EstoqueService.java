package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.Estoque;
import com.inovs.integrprod.model.entity.MovimentoEstoque;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface EstoqueService {

    // Operações básicas de estoque
    Estoque salvar(Estoque estoque);

    Estoque buscarPorId(Long id);

    List<Estoque> listarTodos();

    List<Estoque> buscarPorFilial(Long filialId);

    List<Estoque> buscarPorProduto(Long produtoId);

    List<Estoque> buscarPorFilialEProduto(Long filialId, Long produtoId);

    Estoque buscarPorLote(Long filialId, Long produtoId, String lote);

    void deletar(Long id);

    Estoque atualizar(Long id, Estoque estoque);

    // Movimentações
    MovimentoEstoque entradaEstoque(MovimentoEstoque movimento);

    MovimentoEstoque saidaEstoque(MovimentoEstoque movimento);

    MovimentoEstoque transferenciaEstoque(MovimentoEstoque movimento);

    MovimentoEstoque ajusteEstoque(MovimentoEstoque movimento);

    // Consultas
    BigDecimal consultarSaldo(Long filialId, Long produtoId);

    BigDecimal consultarSaldoDisponivel(Long filialId, Long produtoId);

    List<Estoque> consultarEstoqueAbaixoMinimo();

    List<Estoque> consultarProdutosAVencer(int dias);

    List<Estoque> consultarProdutosVencidos();

    // Relatórios
    List<MovimentoEstoque> consultarMovimentosPeriodo(Long filialId, LocalDate inicio, LocalDate fim);

    List<MovimentoEstoque> consultarMovimentosProduto(Long produtoId);

    void calcularSaldoDiario(Long filialId, LocalDate data);

    // Validações
    void validarEstoque(Estoque estoque);

    void validarMovimento(MovimentoEstoque movimento);
}