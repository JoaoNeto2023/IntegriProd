package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.RoteiroProducao;
import com.inovs.integrprod.service.RoteiroProducaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("roteiros-producao")
@RequiredArgsConstructor
public class RoteiroProducaoController {

    private final RoteiroProducaoService roteiroService;

    @PostMapping
    public ResponseEntity<RoteiroProducao> criar(@Valid @RequestBody RoteiroProducao roteiro) {
        RoteiroProducao novoRoteiro = roteiroService.salvar(roteiro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRoteiro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoteiroProducao> buscarPorId(@PathVariable Long id) {
        RoteiroProducao roteiro = roteiroService.buscarPorId(id);
        return ResponseEntity.ok(roteiro);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<RoteiroProducao>> listarPorProdutoEversao(
            @PathVariable Long produtoId,
            @RequestParam String versao) {
        List<RoteiroProducao> roteiro = roteiroService.listarPorProdutoEversao(produtoId, versao);
        return ResponseEntity.ok(roteiro);
    }

    @GetMapping("/produto/{produtoId}/versoes")
    public ResponseEntity<List<String>> listarVersoes(@PathVariable Long produtoId) {
        List<String> versoes = roteiroService.listarVersoes(produtoId);
        return ResponseEntity.ok(versoes);
    }

    @GetMapping("/produto/{produtoId}/tempo-total")
    public ResponseEntity<Map<String, Object>> calcularTempoTotal(
            @PathVariable Long produtoId,
            @RequestParam String versao) {
        Map<String, Object> tempos = roteiroService.calcularTempoTotal(produtoId, versao);
        return ResponseEntity.ok(tempos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoteiroProducao> atualizar(@PathVariable Long id, @Valid @RequestBody RoteiroProducao roteiro) {
        RoteiroProducao roteiroAtualizado = roteiroService.atualizar(id, roteiro);
        return ResponseEntity.ok(roteiroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        roteiroService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/produto/{produtoId}/copiar-versao")
    public ResponseEntity<Void> copiarVersao(
            @PathVariable Long produtoId,
            @RequestParam String versaoOrigem,
            @RequestParam String versaoDestino) {
        roteiroService.copiarVersao(produtoId, versaoOrigem, versaoDestino);
        return ResponseEntity.ok().build();
    }
}