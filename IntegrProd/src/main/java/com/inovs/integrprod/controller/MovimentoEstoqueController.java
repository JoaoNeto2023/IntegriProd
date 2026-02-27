package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.MovimentoEstoque;
import com.inovs.integrprod.service.MovimentoEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("movimentos-estoque")
@RequiredArgsConstructor
public class MovimentoEstoqueController {

    private final MovimentoEstoqueService movimentoService;

    @GetMapping
    public ResponseEntity<List<MovimentoEstoque>> listarTodos() {
        List<MovimentoEstoque> movimentos = movimentoService.listarTodos();
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentoEstoque> buscarPorId(@PathVariable Long id) {
        MovimentoEstoque movimento = movimentoService.buscarPorId(id);
        return ResponseEntity.ok(movimento);
    }

    @GetMapping("/filial/{filialId}")
    public ResponseEntity<List<MovimentoEstoque>> buscarPorFilial(@PathVariable Long filialId) {
        List<MovimentoEstoque> movimentos = movimentoService.buscarPorFilial(filialId);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentoEstoque>> buscarPorProduto(@PathVariable Long produtoId) {
        List<MovimentoEstoque> movimentos = movimentoService.buscarPorProduto(produtoId);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimentoEstoque>> buscarPorTipo(@PathVariable String tipo) {
        List<MovimentoEstoque> movimentos = movimentoService.buscarPorTipo(tipo);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<MovimentoEstoque>> buscarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<MovimentoEstoque> movimentos = movimentoService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/documento")
    public ResponseEntity<List<MovimentoEstoque>> buscarPorDocumento(
            @RequestParam String tipo,
            @RequestParam String numero) {
        List<MovimentoEstoque> movimentos = movimentoService.buscarPorDocumento(tipo, numero);
        return ResponseEntity.ok(movimentos);
    }

    @GetMapping("/lote/{lote}")
    public ResponseEntity<List<MovimentoEstoque>> buscarPorLote(@PathVariable String lote) {
        List<MovimentoEstoque> movimentos = movimentoService.buscarPorLote(lote);
        return ResponseEntity.ok(movimentos);
    }

    @PostMapping("/{id}/estornar")
    public ResponseEntity<MovimentoEstoque> estornarMovimento(
            @PathVariable Long id,
            @RequestParam String usuario) {
        MovimentoEstoque estorno = movimentoService.estornarMovimento(id, usuario);
        return ResponseEntity.ok(estorno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        movimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}