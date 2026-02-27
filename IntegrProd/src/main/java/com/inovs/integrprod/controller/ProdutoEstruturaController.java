package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.ProdutoEstrutura;
import com.inovs.integrprod.service.ProdutoEstruturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("produtos-estrutura")
@RequiredArgsConstructor
public class ProdutoEstruturaController {

    private final ProdutoEstruturaService estruturaService;

    @PostMapping
    public ResponseEntity<ProdutoEstrutura> criar(@Valid @RequestBody ProdutoEstrutura estrutura) {
        ProdutoEstrutura novaEstrutura = estruturaService.salvar(estrutura);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEstrutura);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoEstrutura> buscarPorId(@PathVariable Long id) {
        ProdutoEstrutura estrutura = estruturaService.buscarPorId(id);
        return ResponseEntity.ok(estrutura);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<ProdutoEstrutura>> listarPorProdutoEversao(
            @PathVariable Long produtoId,
            @RequestParam String versao) {
        List<ProdutoEstrutura> estrutura = estruturaService.listarPorProdutoEversao(produtoId, versao);
        return ResponseEntity.ok(estrutura);
    }

    @GetMapping("/produto/{produtoId}/versoes")
    public ResponseEntity<List<String>> listarVersoes(@PathVariable Long produtoId) {
        List<String> versoes = estruturaService.listarVersoes(produtoId);
        return ResponseEntity.ok(versoes);
    }

    @GetMapping("/produto/{produtoId}/vigente")
    public ResponseEntity<List<ProdutoEstrutura>> listarEstruturaVigente(
            @PathVariable Long produtoId,
            @RequestParam String versao,
            @RequestParam(required = false) LocalDate data) {
        LocalDate dataConsulta = data != null ? data : LocalDate.now();
        List<ProdutoEstrutura> estrutura = estruturaService.listarEstruturaVigente(produtoId, versao, dataConsulta);
        return ResponseEntity.ok(estrutura);
    }

    @GetMapping("/componente/{produtoId}")
    public ResponseEntity<List<ProdutoEstrutura>> listarOndeProdutoEComponente(@PathVariable Long produtoId) {
        List<ProdutoEstrutura> estrutura = estruturaService.listarOndeProdutoEComponente(produtoId);
        return ResponseEntity.ok(estrutura);
    }

    @GetMapping("/produto/{produtoId}/custo")
    public ResponseEntity<Map<String, Object>> calcularCustoEstrutura(
            @PathVariable Long produtoId,
            @RequestParam String versao) {
        Map<String, Object> custo = estruturaService.calcularCustoEstrutura(produtoId, versao);
        return ResponseEntity.ok(custo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoEstrutura> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoEstrutura estrutura) {
        ProdutoEstrutura estruturaAtualizada = estruturaService.atualizar(id, estrutura);
        return ResponseEntity.ok(estruturaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estruturaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/produto/{produtoId}/copiar-versao")
    public ResponseEntity<Void> copiarVersao(
            @PathVariable Long produtoId,
            @RequestParam String versaoOrigem,
            @RequestParam String versaoDestino,
            @RequestParam String usuario) {
        estruturaService.copiarVersao(produtoId, versaoOrigem, versaoDestino, usuario);
        return ResponseEntity.ok().build();
    }
}