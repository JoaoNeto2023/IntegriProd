package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.Pessoa;
import com.inovs.integrprod.model.entity.PessoaEndereco;
import com.inovs.integrprod.model.entity.PessoaClassificacao;
import java.time.LocalDate;
import java.util.List;

public interface PessoaService {

    // Pessoa
    Pessoa salvar(Pessoa pessoa);

    Pessoa buscarPorId(Long id);

    Pessoa buscarPorCpfCnpj(String cpfCnpj);

    List<Pessoa> listarTodas();

    List<Pessoa> listarAtivas();

    List<Pessoa> buscarPorNome(String nome);

    List<Pessoa> buscarPorTipo(String tipoPessoa);

    List<Pessoa> buscarClientes();

    List<Pessoa> buscarFornecedores();

    List<Pessoa> buscarFuncionarios();

    void deletar(Long id);

    Pessoa atualizar(Long id, Pessoa pessoa);

    void validarPessoa(Pessoa pessoa);

    // Endereços
    PessoaEndereco adicionarEndereco(Long pessoaId, PessoaEndereco endereco);

    List<PessoaEndereco> listarEnderecos(Long pessoaId);

    PessoaEndereco buscarEnderecoPrincipal(Long pessoaId);

    void removerEndereco(Long enderecoId);

    PessoaEndereco atualizarEndereco(Long enderecoId, PessoaEndereco endereco);

    // Classificações
    PessoaClassificacao adicionarClassificacao(Long pessoaId, PessoaClassificacao classificacao);

    List<PessoaClassificacao> listarClassificacoes(Long pessoaId);

    void removerClassificacao(Long pessoaId, String tipo);

    PessoaClassificacao atualizarClassificacao(Long pessoaId, String tipo, PessoaClassificacao classificacao);
}