package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.PostoTrabalho;
import com.inovs.integrprod.service.PostoTrabalhoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("postos-trabalho")
@RequiredArgsConstructor
public class PostoTrabalhoController {

    private final PostoTrabalhoService postoTrabalhoService;

    @PostMapping
    public ResponseEntity<PostoTrabalho> criar(@Valid @RequestBody PostoTrabalho postoTrabalho) {
        PostoTrabalho novoPosto = postoTrabalhoService.salvar(postoTrabalho);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPosto);
    }

    @GetMapping
    public ResponseEntity<List<PostoTrabalho>> listarTodos() {
        List<PostoTrabalho> postos = postoTrabalhoService.listarTodos();
        return ResponseEntity.ok(postos);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<PostoTrabalho>> listarAtivos() {
        List<PostoTrabalho> postos = postoTrabalhoService.listarAtivos();
        return ResponseEntity.ok(postos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostoTrabalho> buscarPorId(@PathVariable Long id) {
        PostoTrabalho posto = postoTrabalhoService.buscarPorId(id);
        return ResponseEntity.ok(posto);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<PostoTrabalho> buscarPorCodigo(@PathVariable String codigo) {
        PostoTrabalho posto = postoTrabalhoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(posto);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<PostoTrabalho>> buscarPorNome(@RequestParam String nome) {
        List<PostoTrabalho> postos = postoTrabalhoService.buscarPorNome(nome);
        return ResponseEntity.ok(postos);
    }

    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<PostoTrabalho>> buscarPorSetor(@PathVariable String setor) {
        List<PostoTrabalho> postos = postoTrabalhoService.buscarPorSetor(setor);
        return ResponseEntity.ok(postos);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PostoTrabalho>> buscarPorStatus(@PathVariable String status) {
        List<PostoTrabalho> postos = postoTrabalhoService.buscarPorStatus(status);
        return ResponseEntity.ok(postos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostoTrabalho> atualizar(@PathVariable Long id, @Valid @RequestBody PostoTrabalho postoTrabalho) {
        PostoTrabalho postoAtualizado = postoTrabalhoService.atualizar(id, postoTrabalho);
        return ResponseEntity.ok(postoAtualizado);
    }

    @PatchMapping("/{id}/custo")
    public ResponseEntity<PostoTrabalho> atualizarCusto(
            @PathVariable Long id,
            @RequestParam BigDecimal custoHora,
            @RequestParam(required = false) BigDecimal custoFixoHora) {
        PostoTrabalho postoAtualizado = postoTrabalhoService.atualizarCusto(id, custoHora, custoFixoHora);
        return ResponseEntity.ok(postoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        postoTrabalhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}