package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.FamiliaProduto;
import com.inovs.integrprod.service.FamiliaProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/familias-produto")
@RequiredArgsConstructor
public class FamiliaProdutoController {

    private final FamiliaProdutoService familiaProdutoService;

    @PostMapping
    public ResponseEntity<FamiliaProduto> criar(@Valid @RequestBody FamiliaProduto familiaProduto) {
        FamiliaProduto novaFamilia = familiaProdutoService.salvar(familiaProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFamilia);
    }

    @GetMapping
    public ResponseEntity<List<FamiliaProduto>> listarTodas() {
        List<FamiliaProduto> familias = familiaProdutoService.listarTodas();
        return ResponseEntity.ok(familias);
    }

    @GetMapping("/raizes")
    public ResponseEntity<List<FamiliaProduto>> listarRaizes() {
        List<FamiliaProduto> familias = familiaProdutoService.listarFamiliasRaiz();
        return ResponseEntity.ok(familias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamiliaProduto> buscarPorId(@PathVariable Long id) {
        FamiliaProduto familia = familiaProdutoService.buscarPorId(id);
        return ResponseEntity.ok(familia);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<FamiliaProduto> buscarPorCodigo(@PathVariable String codigo) {
        FamiliaProduto familia = familiaProdutoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(familia);
    }

    @GetMapping("/{id}/subfamilias")
    public ResponseEntity<List<FamiliaProduto>> listarSubfamilias(@PathVariable Long id) {
        List<FamiliaProduto> subfamilias = familiaProdutoService.buscarSubfamilias(id);
        return ResponseEntity.ok(subfamilias);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<FamiliaProduto>> buscarPorNome(@RequestParam String nome) {
        List<FamiliaProduto> familias = familiaProdutoService.buscarPorNome(nome);
        return ResponseEntity.ok(familias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamiliaProduto> atualizar(@PathVariable Long id, @Valid @RequestBody FamiliaProduto familiaProduto) {
        FamiliaProduto familiaAtualizada = familiaProdutoService.atualizar(id, familiaProduto);
        return ResponseEntity.ok(familiaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        familiaProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}