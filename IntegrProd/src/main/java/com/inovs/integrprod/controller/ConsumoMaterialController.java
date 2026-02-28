package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.ConsumoMaterial;
import com.inovs.integrprod.service.ConsumoMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/consumos")
@RequiredArgsConstructor
public class ConsumoMaterialController {

    private final ConsumoMaterialService consumoService;

    @PostMapping
    public ResponseEntity<ConsumoMaterial> criar(@Valid @RequestBody ConsumoMaterial consumo) {
        ConsumoMaterial novoConsumo = consumoService.salvar(consumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoConsumo);
    }

    @GetMapping
    public ResponseEntity<List<ConsumoMaterial>> listarTodos() {
        List<ConsumoMaterial> consumos = consumoService.listarTodos();
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumoMaterial> buscarPorId(@PathVariable Long id) {
        ConsumoMaterial consumo = consumoService.buscarPorId(id);
        return ResponseEntity.ok(consumo);
    }

    @GetMapping("/ordem/{ordemId}")
    public ResponseEntity<List<ConsumoMaterial>> buscarPorOrdem(@PathVariable Long ordemId) {
        List<ConsumoMaterial> consumos = consumoService.buscarPorOrdem(ordemId);
        return ResponseEntity.ok(consumos);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<ConsumoMaterial>> buscarPorProduto(@PathVariable Long produtoId) {
        List<ConsumoMaterial> consumos = consumoService.buscarPorProduto(produtoId);
        return ResponseEntity.ok(consumos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumoMaterial> atualizar(@PathVariable Long id, @Valid @RequestBody ConsumoMaterial consumo) {
        ConsumoMaterial consumoAtualizado = consumoService.atualizar(id, consumo);
        return ResponseEntity.ok(consumoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consumoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}