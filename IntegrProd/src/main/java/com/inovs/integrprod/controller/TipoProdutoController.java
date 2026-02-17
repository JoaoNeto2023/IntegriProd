package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.TipoProduto;
import com.inovs.integrprod.service.TipoProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("tipos-produto")
@RequiredArgsConstructor
public class TipoProdutoController {

    private final TipoProdutoService tipoProdutoService;

    @PostMapping
    public ResponseEntity<TipoProduto> criar(@Valid @RequestBody TipoProduto tipoProduto) {
        TipoProduto novoTipo = tipoProdutoService.salvar(tipoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoTipo);
    }

    @GetMapping
    public ResponseEntity<List<TipoProduto>> listarTodos() {
        List<TipoProduto> tipos = tipoProdutoService.listarTodos();
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProduto> buscarPorId(@PathVariable Long id) {
        TipoProduto tipo = tipoProdutoService.buscarPorId(id);
        return ResponseEntity.ok(tipo);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<TipoProduto> buscarPorCodigo(@PathVariable String codigo) {
        TipoProduto tipo = tipoProdutoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(tipo);
    }

    @GetMapping("/natureza/{natureza}")
    public ResponseEntity<List<TipoProduto>> buscarPorNatureza(@PathVariable String natureza) {
        List<TipoProduto> tipos = tipoProdutoService.buscarPorNatureza(natureza);
        return ResponseEntity.ok(tipos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProduto> atualizar(@PathVariable Long id, @Valid @RequestBody TipoProduto tipoProduto) {
        TipoProduto tipoAtualizado = tipoProdutoService.atualizar(id, tipoProduto);
        return ResponseEntity.ok(tipoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        tipoProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}