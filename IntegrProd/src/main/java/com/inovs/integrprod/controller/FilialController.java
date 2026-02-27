package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.Filial;
import com.inovs.integrprod.service.FilialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("filiais")
@RequiredArgsConstructor
public class FilialController {

    private final FilialService filialService;

    @PostMapping
    public ResponseEntity<Filial> criar(@Valid @RequestBody Filial filial) {
        Filial novaFilial = filialService.salvar(filial);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFilial);
    }

    @GetMapping
    public ResponseEntity<List<Filial>> listarTodas() {
        List<Filial> filiais = filialService.listarTodas();
        return ResponseEntity.ok(filiais);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<Filial>> listarAtivas() {
        List<Filial> filiais = filialService.listarAtivas();
        return ResponseEntity.ok(filiais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filial> buscarPorId(@PathVariable Long id) {
        Filial filial = filialService.buscarPorId(id);
        return ResponseEntity.ok(filial);
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Filial> buscarPorCnpj(@PathVariable String cnpj) {
        Filial filial = filialService.buscarPorCnpj(cnpj);
        return ResponseEntity.ok(filial);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Filial> buscarPorCodigo(@PathVariable String codigo) {
        Filial filial = filialService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(filial);
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<List<Filial>> buscarPorEmpresa(@PathVariable Long empresaId) {
        List<Filial> filiais = filialService.buscarPorEmpresa(empresaId);
        return ResponseEntity.ok(filiais);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Filial>> buscarPorNome(@RequestParam String nome) {
        List<Filial> filiais = filialService.buscarPorNome(nome);
        return ResponseEntity.ok(filiais);
    }

    @GetMapping("/uf/{uf}")
    public ResponseEntity<List<Filial>> buscarPorUf(@PathVariable String uf) {
        List<Filial> filiais = filialService.buscarPorUf(uf);
        return ResponseEntity.ok(filiais);
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<Filial>> buscarPorCidade(@PathVariable String cidade) {
        List<Filial> filiais = filialService.buscarPorCidade(cidade);
        return ResponseEntity.ok(filiais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filial> atualizar(@PathVariable Long id, @Valid @RequestBody Filial filial) {
        Filial filialAtualizada = filialService.atualizar(id, filial);
        return ResponseEntity.ok(filialAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        filialService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}