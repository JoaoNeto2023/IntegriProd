package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.Produto;
import com.inovs.integrprod.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto) {
        Produto novoProduto = produtoService.salvar(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        List<Produto> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Produto>> listarAtivos() {
        List<Produto> produtos = produtoService.listarAtivos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable String codigo) {
        Produto produto = produtoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/tipo/{tipoProdutoId}")
    public ResponseEntity<List<Produto>> buscarPorTipo(@PathVariable Long tipoProdutoId) {
        List<Produto> produtos = produtoService.buscarPorTipoProduto(tipoProdutoId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/familia/{familiaId}")
    public ResponseEntity<List<Produto>> buscarPorFamilia(@PathVariable Long familiaId) {
        List<Produto> produtos = produtoService.buscarPorFamilia(familiaId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar/preco")
    public ResponseEntity<List<Produto>> buscarPorPreco(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        List<Produto> produtos = produtoService.buscarPorPrecoEntre(min, max);
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.atualizar(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<Produto> atualizarPreco(
            @PathVariable Long id,
            @RequestParam BigDecimal preco) {
        Produto produtoAtualizado = produtoService.atualizarPreco(id, preco);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Produto> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Produto produtoAtualizado = produtoService.atualizarStatus(id, status);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}