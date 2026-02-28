package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.OrdemProducao;
import com.inovs.integrprod.service.OrdemProducaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/ordens-producao")
@RequiredArgsConstructor
public class OrdemProducaoController {

    private final OrdemProducaoService ordemService;

    @PostMapping
    public ResponseEntity<OrdemProducao> criar(@Valid @RequestBody OrdemProducao ordem) {
        OrdemProducao novaOrdem = ordemService.salvar(ordem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOrdem);
    }

    @GetMapping
    public ResponseEntity<List<OrdemProducao>> listarTodas() {
        List<OrdemProducao> ordens = ordemService.listarTodas();
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemProducao> buscarPorId(@PathVariable Long id) {
        OrdemProducao ordem = ordemService.buscarPorId(id);
        return ResponseEntity.ok(ordem);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemProducao>> buscarPorStatus(@PathVariable String status) {
        List<OrdemProducao> ordens = ordemService.buscarPorStatus(status);
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<OrdemProducao>> buscarPorProduto(@PathVariable Long produtoId) {
        List<OrdemProducao> ordens = ordemService.buscarPorProduto(produtoId);
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<OrdemProducao>> buscarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<OrdemProducao> ordens = ordemService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/abertas")
    public ResponseEntity<List<OrdemProducao>> buscarAbertas() {
        List<OrdemProducao> ordens = ordemService.buscarAbertas();
        return ResponseEntity.ok(ordens);
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<OrdemProducao>> buscarAtrasadas() {
        List<OrdemProducao> ordens = ordemService.buscarAtrasadas();
        return ResponseEntity.ok(ordens);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemProducao> atualizar(@PathVariable Long id, @Valid @RequestBody OrdemProducao ordem) {
        OrdemProducao ordemAtualizada = ordemService.atualizar(id, ordem);
        return ResponseEntity.ok(ordemAtualizada);
    }

    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciar(@PathVariable Long id) {
        ordemService.iniciar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/pausar")
    public ResponseEntity<Void> pausar(@PathVariable Long id) {
        ordemService.pausar(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/concluir")
    public ResponseEntity<Void> concluir(@PathVariable Long id) {
        ordemService.concluir(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        ordemService.cancelar(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ordemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}