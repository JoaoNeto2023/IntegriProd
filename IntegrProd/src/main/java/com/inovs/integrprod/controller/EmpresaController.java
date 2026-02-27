package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.Empresa;
import com.inovs.integrprod.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<Empresa> criar(@Valid @RequestBody Empresa empresa) {
        Empresa novaEmpresa = empresaService.salvar(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarTodas() {
        List<Empresa> empresas = empresaService.listarTodas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<Empresa>> listarAtivas() {
        List<Empresa> empresas = empresaService.listarAtivas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Empresa> buscarPorCnpj(@PathVariable String cnpj) {
        Empresa empresa = empresaService.buscarPorCnpj(cnpj);
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("/buscar/razao-social")
    public ResponseEntity<List<Empresa>> buscarPorRazaoSocial(@RequestParam String razaoSocial) {
        List<Empresa> empresas = empresaService.buscarPorRazaoSocial(razaoSocial);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/regime/{regime}")
    public ResponseEntity<List<Empresa>> buscarPorRegimeTributario(@PathVariable String regime) {
        List<Empresa> empresas = empresaService.buscarPorRegimeTributario(regime);
        return ResponseEntity.ok(empresas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @Valid @RequestBody Empresa empresa) {
        Empresa empresaAtualizada = empresaService.atualizar(id, empresa);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}