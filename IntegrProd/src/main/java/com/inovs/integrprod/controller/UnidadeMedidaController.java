package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.UnidadeMedida;
import com.inovs.integrprod.service.UnidadeMedidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("unidades-medida")
@RequiredArgsConstructor
public class UnidadeMedidaController {

    private final UnidadeMedidaService unidadeMedidaService;

    @PostMapping
    public ResponseEntity<UnidadeMedida> criar(@Valid @RequestBody UnidadeMedida unidadeMedida) {
        UnidadeMedida novaUnidade = unidadeMedidaService.salvar(unidadeMedida);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaUnidade);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeMedida>> listarTodas() {
        List<UnidadeMedida> unidades = unidadeMedidaService.listarTodas();
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/{sigla}")
    public ResponseEntity<UnidadeMedida> buscarPorSigla(@PathVariable String sigla) {
        UnidadeMedida unidade = unidadeMedidaService.buscarPorSigla(sigla);
        return ResponseEntity.ok(unidade);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UnidadeMedida>> buscarPorDescricao(@RequestParam String descricao) {
        List<UnidadeMedida> unidades = unidadeMedidaService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(unidades);
    }

    @PutMapping("/{sigla}")
    public ResponseEntity<UnidadeMedida> atualizar(@PathVariable String sigla,
                                                   @Valid @RequestBody UnidadeMedida unidadeMedida) {
        UnidadeMedida unidadeAtualizada = unidadeMedidaService.atualizar(sigla, unidadeMedida);
        return ResponseEntity.ok(unidadeAtualizada);
    }

    @DeleteMapping("/{sigla}")
    public ResponseEntity<Void> deletar(@PathVariable String sigla) {
        unidadeMedidaService.deletar(sigla);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/teste")
    public String teste() {
        return "Controller de UnidadeMedida está funcionando!";
    }
}