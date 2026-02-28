package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.ApontamentoProducao;
import com.inovs.integrprod.service.ApontamentoProducaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/apontamentos")
@RequiredArgsConstructor
public class ApontamentoProducaoController {

    private final ApontamentoProducaoService apontamentoService;

    @PostMapping
    public ResponseEntity<ApontamentoProducao> criar(@Valid @RequestBody ApontamentoProducao apontamento) {
        ApontamentoProducao novoApontamento = apontamentoService.salvar(apontamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoApontamento);
    }

    @GetMapping
    public ResponseEntity<List<ApontamentoProducao>> listarTodos() {
        List<ApontamentoProducao> apontamentos = apontamentoService.listarTodos();
        return ResponseEntity.ok(apontamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApontamentoProducao> buscarPorId(@PathVariable Long id) {
        ApontamentoProducao apontamento = apontamentoService.buscarPorId(id);
        return ResponseEntity.ok(apontamento);
    }

    @GetMapping("/ordem/{ordemId}")
    public ResponseEntity<List<ApontamentoProducao>> buscarPorOrdem(@PathVariable Long ordemId) {
        List<ApontamentoProducao> apontamentos = apontamentoService.buscarPorOrdem(ordemId);
        return ResponseEntity.ok(apontamentos);
    }

    @GetMapping("/posto/{postoTrabalhoId}")
    public ResponseEntity<List<ApontamentoProducao>> buscarPorPostoTrabalho(@PathVariable Long postoTrabalhoId) {
        List<ApontamentoProducao> apontamentos = apontamentoService.buscarPorPostoTrabalho(postoTrabalhoId);
        return ResponseEntity.ok(apontamentos);
    }

    @GetMapping("/data/{data}")
    public ResponseEntity<List<ApontamentoProducao>> buscarPorData(@PathVariable String data) {
        LocalDate dataBusca = LocalDate.parse(data);
        List<ApontamentoProducao> apontamentos = apontamentoService.buscarPorData(dataBusca);
        return ResponseEntity.ok(apontamentos);
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<ApontamentoProducao>> buscarPorPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim) {
        List<ApontamentoProducao> apontamentos = apontamentoService.buscarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(apontamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApontamentoProducao> atualizar(@PathVariable Long id, @Valid @RequestBody ApontamentoProducao apontamento) {
        ApontamentoProducao apontamentoAtualizado = apontamentoService.atualizar(id, apontamento);
        return ResponseEntity.ok(apontamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        apontamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}