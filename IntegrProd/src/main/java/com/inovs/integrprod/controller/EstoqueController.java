package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.Estoque;
import com.inovs.integrprod.model.entity.MovimentoEstoque;
import com.inovs.integrprod.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    // ========== OPERAÇÕES BÁSICAS ==========

    @PostMapping
    public ResponseEntity<Estoque> criar(@Valid @RequestBody Estoque estoque) {
        Estoque novoEstoque = estoqueService.salvar(estoque);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstoque);
    }

    @GetMapping
    public ResponseEntity<List<Estoque>> listarTodos() {
        List<Estoque> estoques = estoqueService.listarTodos();
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estoque> buscarPorId(@PathVariable Long id) {
        Estoque estoque = estoqueService.buscarPorId(id);
        return ResponseEntity.ok(estoque);
    }

    @GetMapping("/filial/{filialId}")
    public ResponseEntity<List<Estoque>> buscarPorFilial(@PathVariable Long filialId) {
        List<Estoque> estoques = estoqueService.buscarPorFilial(filialId);
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<Estoque>> buscarPorProduto(@PathVariable Long produtoId) {
        List<Estoque> estoques = estoqueService.buscarPorProduto(produtoId);
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/filial/{filialId}/produto/{produtoId}")
    public ResponseEntity<List<Estoque>> buscarPorFilialEProduto(
            @PathVariable Long filialId,
            @PathVariable Long produtoId) {
        List<Estoque> estoques = estoqueService.buscarPorFilialEProduto(filialId, produtoId);
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/lote")
    public ResponseEntity<Estoque> buscarPorLote(
            @RequestParam Long filialId,
            @RequestParam Long produtoId,
            @RequestParam String lote) {
        Estoque estoque = estoqueService.buscarPorLote(filialId, produtoId, lote);
        return ResponseEntity.ok(estoque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estoque> atualizar(@PathVariable Long id, @Valid @RequestBody Estoque estoque) {
        Estoque estoqueAtualizado = estoqueService.atualizar(id, estoque);
        return ResponseEntity.ok(estoqueAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estoqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ========== MOVIMENTAÇÕES ==========

    @PostMapping("/movimentos/entrada")
    public ResponseEntity<MovimentoEstoque> entradaEstoque(@Valid @RequestBody MovimentoEstoque movimento) {
        MovimentoEstoque movimentoSalvo = estoqueService.entradaEstoque(movimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentoSalvo);
    }

    @PostMapping("/movimentos/saida")
    public ResponseEntity<MovimentoEstoque> saidaEstoque(@Valid @RequestBody MovimentoEstoque movimento) {
        MovimentoEstoque movimentoSalvo = estoqueService.saidaEstoque(movimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentoSalvo);
    }

    @PostMapping("/movimentos/transferencia")
    public ResponseEntity<MovimentoEstoque> transferenciaEstoque(@Valid @RequestBody MovimentoEstoque movimento) {
        MovimentoEstoque movimentoSalvo = estoqueService.transferenciaEstoque(movimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentoSalvo);
    }

    @PostMapping("/movimentos/ajuste")
    public ResponseEntity<MovimentoEstoque> ajusteEstoque(@Valid @RequestBody MovimentoEstoque movimento) {
        MovimentoEstoque movimentoSalvo = estoqueService.ajusteEstoque(movimento);
        return ResponseEntity.status(HttpStatus.CREATED).body(movimentoSalvo);
    }

    @GetMapping("/movimentos")
    public ResponseEntity<List<MovimentoEstoque>> listarMovimentos() {
        // Implementar se necessário
        return ResponseEntity.ok().build();
    }

    @GetMapping("/movimentos/produto/{produtoId}")
    public ResponseEntity<List<MovimentoEstoque>> consultarMovimentosProduto(@PathVariable Long produtoId) {
        List<MovimentoEstoque> movimentos = estoqueService.consultarMovimentosProduto(produtoId);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/movimentos/periodo")
    public ResponseEntity<List<MovimentoEstoque>> consultarMovimentosPeriodo(
            @RequestParam Long filialId,
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<MovimentoEstoque> movimentos = estoqueService.consultarMovimentosPeriodo(filialId, inicio, fim);
        return ResponseEntity.ok(movimentos);
    }

    // ========== CONSULTAS ESPECÍFICAS ==========

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(
            @RequestParam Long filialId,
            @RequestParam Long produtoId) {
        BigDecimal saldo = estoqueService.consultarSaldo(filialId, produtoId);
        return ResponseEntity.ok(saldo);
    }

    @GetMapping("/disponivel")
    public ResponseEntity<BigDecimal> consultarSaldoDisponivel(
            @RequestParam Long filialId,
            @RequestParam Long produtoId) {
        BigDecimal disponivel = estoqueService.consultarSaldoDisponivel(filialId, produtoId);
        return ResponseEntity.ok(disponivel);
    }

    @GetMapping("/alertas/abaixo-minimo")
    public ResponseEntity<List<Estoque>> consultarEstoqueAbaixoMinimo() {
        List<Estoque> estoques = estoqueService.consultarEstoqueAbaixoMinimo();
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/alertas/a-vencer")
    public ResponseEntity<List<Estoque>> consultarProdutosAVencer(@RequestParam(defaultValue = "30") int dias) {
        List<Estoque> estoques = estoqueService.consultarProdutosAVencer(dias);
        return ResponseEntity.ok(estoques);
    }

    @GetMapping("/alertas/vencidos")
    public ResponseEntity<List<Estoque>> consultarProdutosVencidos() {
        List<Estoque> estoques = estoqueService.consultarProdutosVencidos();
        return ResponseEntity.ok(estoques);
    }

    // ========== RELATÓRIOS ==========

    @PostMapping("/relatorios/calcular-saldo-diario")
    public ResponseEntity<Void> calcularSaldoDiario(
            @RequestParam Long filialId,
            @RequestParam LocalDate data) {
        estoqueService.calcularSaldoDiario(filialId, data);
        return ResponseEntity.ok().build();
    }
}