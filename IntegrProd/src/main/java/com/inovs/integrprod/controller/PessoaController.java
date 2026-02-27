package com.inovs.integrprod.controller;

import com.inovs.integrprod.model.entity.Pessoa;
import com.inovs.integrprod.model.entity.PessoaEndereco;
import com.inovs.integrprod.model.entity.PessoaClassificacao;
import com.inovs.integrprod.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;

    // ========== PESSOA ==========

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.salvar(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarTodas() {
        List<Pessoa> pessoas = pessoaService.listarTodas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<Pessoa>> listarAtivas() {
        List<Pessoa> pessoas = pessoaService.listarAtivas();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPorId(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.buscarPorId(id);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/cpf-cnpj/{cpfCnpj}")
    public ResponseEntity<Pessoa> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        Pessoa pessoa = pessoaService.buscarPorCpfCnpj(cpfCnpj);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Pessoa>> buscarPorNome(@RequestParam String nome) {
        List<Pessoa> pessoas = pessoaService.buscarPorNome(nome);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Pessoa>> buscarPorTipo(@PathVariable String tipo) {
        List<Pessoa> pessoas = pessoaService.buscarPorTipo(tipo);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Pessoa>> buscarClientes() {
        List<Pessoa> pessoas = pessoaService.buscarClientes();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/fornecedores")
    public ResponseEntity<List<Pessoa>> buscarFornecedores() {
        List<Pessoa> pessoas = pessoaService.buscarFornecedores();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/funcionarios")
    public ResponseEntity<List<Pessoa>> buscarFuncionarios() {
        List<Pessoa> pessoas = pessoaService.buscarFuncionarios();
        return ResponseEntity.ok(pessoas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.atualizar(id, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pessoaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ========== ENDEREÇOS ==========

    @PostMapping("/{pessoaId}/enderecos")
    public ResponseEntity<PessoaEndereco> adicionarEndereco(
            @PathVariable Long pessoaId,
            @Valid @RequestBody PessoaEndereco endereco) {
        PessoaEndereco novoEndereco = pessoaService.adicionarEndereco(pessoaId, endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
    }

    @GetMapping("/{pessoaId}/enderecos")
    public ResponseEntity<List<PessoaEndereco>> listarEnderecos(@PathVariable Long pessoaId) {
        List<PessoaEndereco> enderecos = pessoaService.listarEnderecos(pessoaId);
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{pessoaId}/enderecos/principal")
    public ResponseEntity<PessoaEndereco> buscarEnderecoPrincipal(@PathVariable Long pessoaId) {
        PessoaEndereco endereco = pessoaService.buscarEnderecoPrincipal(pessoaId);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/enderecos/{enderecoId}")
    public ResponseEntity<PessoaEndereco> atualizarEndereco(
            @PathVariable Long enderecoId,
            @Valid @RequestBody PessoaEndereco endereco) {
        PessoaEndereco enderecoAtualizado = pessoaService.atualizarEndereco(enderecoId, endereco);
        return ResponseEntity.ok(enderecoAtualizado);
    }

    @DeleteMapping("/enderecos/{enderecoId}")
    public ResponseEntity<Void> removerEndereco(@PathVariable Long enderecoId) {
        pessoaService.removerEndereco(enderecoId);
        return ResponseEntity.noContent().build();
    }

    // ========== CLASSIFICAÇÕES ==========

    @PostMapping("/{pessoaId}/classificacoes")
    public ResponseEntity<PessoaClassificacao> adicionarClassificacao(
            @PathVariable Long pessoaId,
            @Valid @RequestBody PessoaClassificacao classificacao) {
        PessoaClassificacao novaClassificacao = pessoaService.adicionarClassificacao(pessoaId, classificacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaClassificacao);
    }

    @GetMapping("/{pessoaId}/classificacoes")
    public ResponseEntity<List<PessoaClassificacao>> listarClassificacoes(@PathVariable Long pessoaId) {
        List<PessoaClassificacao> classificacoes = pessoaService.listarClassificacoes(pessoaId);
        return ResponseEntity.ok(classificacoes);
    }

    @PutMapping("/{pessoaId}/classificacoes/{tipo}")
    public ResponseEntity<PessoaClassificacao> atualizarClassificacao(
            @PathVariable Long pessoaId,
            @PathVariable String tipo,
            @Valid @RequestBody PessoaClassificacao classificacao) {
        PessoaClassificacao classificacaoAtualizada = pessoaService.atualizarClassificacao(pessoaId, tipo, classificacao);
        return ResponseEntity.ok(classificacaoAtualizada);
    }

    @DeleteMapping("/{pessoaId}/classificacoes/{tipo}")
    public ResponseEntity<Void> removerClassificacao(
            @PathVariable Long pessoaId,
            @PathVariable String tipo) {
        pessoaService.removerClassificacao(pessoaId, tipo);
        return ResponseEntity.noContent().build();
    }
}