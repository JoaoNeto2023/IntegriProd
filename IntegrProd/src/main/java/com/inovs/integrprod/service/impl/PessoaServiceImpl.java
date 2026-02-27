package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.Pessoa;
import com.inovs.integrprod.model.entity.PessoaEndereco;
import com.inovs.integrprod.model.entity.PessoaClassificacao;
import com.inovs.integrprod.repository.PessoaRepository;
import com.inovs.integrprod.repository.PessoaEnderecoRepository;
import com.inovs.integrprod.repository.PessoaClassificacaoRepository;
import com.inovs.integrprod.service.PessoaService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaEnderecoRepository enderecoRepository;
    private final PessoaClassificacaoRepository classificacaoRepository;

    // ========== PESSOA ==========

    @Override
    public Pessoa salvar(Pessoa pessoa) {
        validarPessoa(pessoa);

        if (pessoaRepository.existsByCpfCnpj(pessoa.getCpfCnpj())) {
            throw new BusinessException("Já existe uma pessoa com o CPF/CNPJ: " + pessoa.getCpfCnpj());
        }

        if (pessoa.getSituacao() == null) {
            pessoa.setSituacao("ATIVO");
        }

        return pessoaRepository.save(pessoa);
    }

    @Override
    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com ID: " + id));
    }

    @Override
    public Pessoa buscarPorCpfCnpj(String cpfCnpj) {
        return pessoaRepository.findByCpfCnpj(cpfCnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com CPF/CNPJ: " + cpfCnpj));
    }

    @Override
    public List<Pessoa> listarTodas() {
        return pessoaRepository.findAll();
    }

    @Override
    public List<Pessoa> listarAtivas() {
        return pessoaRepository.findBySituacao("ATIVO");
    }

    @Override
    public List<Pessoa> buscarPorNome(String nome) {
        return pessoaRepository.findByNomeRazaoContainingIgnoreCase(nome);
    }

    @Override
    public List<Pessoa> buscarPorTipo(String tipoPessoa) {
        return pessoaRepository.findByTipoPessoa(tipoPessoa);
    }

    @Override
    public List<Pessoa> buscarClientes() {
        return pessoaRepository.findClientes();
    }

    @Override
    public List<Pessoa> buscarFornecedores() {
        return pessoaRepository.findFornecedores();
    }

    @Override
    public List<Pessoa> buscarFuncionarios() {
        return pessoaRepository.findFuncionarios();
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Pessoa pessoa = buscarPorId(id);

        // Verificar se tem endereços
        List<PessoaEndereco> enderecos = enderecoRepository.findByPessoaId(id);
        if (!enderecos.isEmpty()) {
            enderecoRepository.deleteAll(enderecos);
        }

        // Verificar se tem classificações
        List<PessoaClassificacao> classificacoes = classificacaoRepository.findByPessoaId(id);
        if (!classificacoes.isEmpty()) {
            classificacaoRepository.deleteAll(classificacoes);
        }

        pessoaRepository.delete(pessoa);
    }

    @Override
    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoaExistente = buscarPorId(id);

        if (!pessoaExistente.getCpfCnpj().equals(pessoaAtualizada.getCpfCnpj()) &&
                pessoaRepository.existsByCpfCnpj(pessoaAtualizada.getCpfCnpj())) {
            throw new BusinessException("Já existe uma pessoa com o CPF/CNPJ: " + pessoaAtualizada.getCpfCnpj());
        }

        pessoaExistente.setTipoPessoa(pessoaAtualizada.getTipoPessoa());
        pessoaExistente.setNomeRazao(pessoaAtualizada.getNomeRazao());
        pessoaExistente.setNomeFantasia(pessoaAtualizada.getNomeFantasia());
        pessoaExistente.setCpfCnpj(pessoaAtualizada.getCpfCnpj());
        pessoaExistente.setRgIe(pessoaAtualizada.getRgIe());
        pessoaExistente.setDataNascimento(pessoaAtualizada.getDataNascimento());
        pessoaExistente.setSexo(pessoaAtualizada.getSexo());
        pessoaExistente.setEstadoCivil(pessoaAtualizada.getEstadoCivil());
        pessoaExistente.setProfissao(pessoaAtualizada.getProfissao());
        pessoaExistente.setEmail(pessoaAtualizada.getEmail());
        pessoaExistente.setTelefone1(pessoaAtualizada.getTelefone1());
        pessoaExistente.setTelefone2(pessoaAtualizada.getTelefone2());
        pessoaExistente.setCelular(pessoaAtualizada.getCelular());
        pessoaExistente.setSite(pessoaAtualizada.getSite());
        pessoaExistente.setObservacoes(pessoaAtualizada.getObservacoes());
        pessoaExistente.setSituacao(pessoaAtualizada.getSituacao());

        return pessoaRepository.save(pessoaExistente);
    }

    @Override
    public void validarPessoa(Pessoa pessoa) {
        if (pessoa.getNomeRazao() == null || pessoa.getNomeRazao().trim().isEmpty()) {
            throw new BusinessException("Nome/Razão social é obrigatório");
        }

        if (pessoa.getCpfCnpj() == null || pessoa.getCpfCnpj().trim().isEmpty()) {
            throw new BusinessException("CPF/CNPJ é obrigatório");
        }

        if (pessoa.getTipoPessoa() == null || pessoa.getTipoPessoa().trim().isEmpty()) {
            throw new BusinessException("Tipo de pessoa (PF/PJ) é obrigatório");
        }

        // Validar formato do CPF/CNPJ
        String documentoLimpo = pessoa.getCpfCnpj().replaceAll("[^0-9]", "");
        if (pessoa.getTipoPessoa().equals("PF") && documentoLimpo.length() != 11) {
            throw new BusinessException("CPF inválido. Deve conter 11 dígitos.");
        }
        if (pessoa.getTipoPessoa().equals("PJ") && documentoLimpo.length() != 14) {
            throw new BusinessException("CNPJ inválido. Deve conter 14 dígitos.");
        }
    }

    // ========== ENDEREÇOS ==========

    @Override
    @Transactional
    public PessoaEndereco adicionarEndereco(Long pessoaId, PessoaEndereco endereco) {
        Pessoa pessoa = buscarPorId(pessoaId);
        endereco.setPessoa(pessoa);

        // Se for endereço principal, resetar outros principais
        if (endereco.getPrincipal() != null && endereco.getPrincipal()) {
            enderecoRepository.resetarEnderecoPrincipal(pessoaId);
        }

        return enderecoRepository.save(endereco);
    }

    @Override
    public List<PessoaEndereco> listarEnderecos(Long pessoaId) {
        return enderecoRepository.findByPessoaId(pessoaId);
    }

    @Override
    public PessoaEndereco buscarEnderecoPrincipal(Long pessoaId) {
        return enderecoRepository.findEnderecoPrincipal(pessoaId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço principal não encontrado para a pessoa: " + pessoaId));
    }

    @Override
    @Transactional
    public void removerEndereco(Long enderecoId) {
        if (!enderecoRepository.existsById(enderecoId)) {
            throw new ResourceNotFoundException("Endereço não encontrado com ID: " + enderecoId);
        }
        enderecoRepository.deleteById(enderecoId);
    }

    @Override
    @Transactional
    public PessoaEndereco atualizarEndereco(Long enderecoId, PessoaEndereco enderecoAtualizado) {
        PessoaEndereco enderecoExistente = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + enderecoId));

        // Se for atualizar para principal, resetar outros principais da mesma pessoa
        if (enderecoAtualizado.getPrincipal() != null && enderecoAtualizado.getPrincipal() &&
                !enderecoExistente.getPrincipal()) {
            enderecoRepository.resetarEnderecoPrincipal(enderecoExistente.getPessoa().getId());
        }

        enderecoExistente.setTipoEndereco(enderecoAtualizado.getTipoEndereco());
        enderecoExistente.setLogradouro(enderecoAtualizado.getLogradouro());
        enderecoExistente.setNumero(enderecoAtualizado.getNumero());
        enderecoExistente.setComplemento(enderecoAtualizado.getComplemento());
        enderecoExistente.setBairro(enderecoAtualizado.getBairro());
        enderecoExistente.setCidade(enderecoAtualizado.getCidade());
        enderecoExistente.setUf(enderecoAtualizado.getUf());
        enderecoExistente.setCep(enderecoAtualizado.getCep());
        enderecoExistente.setPrincipal(enderecoAtualizado.getPrincipal());

        return enderecoRepository.save(enderecoExistente);
    }

    // ========== CLASSIFICAÇÕES ==========

    @Override
    @Transactional
    public PessoaClassificacao adicionarClassificacao(Long pessoaId, PessoaClassificacao classificacao) {
        Pessoa pessoa = buscarPorId(pessoaId);
        classificacao.setPessoa(pessoa);

        if (classificacaoRepository.existsByPessoaIdAndTipo(pessoaId, classificacao.getTipo())) {
            throw new BusinessException("Pessoa já possui classificação do tipo: " + classificacao.getTipo());
        }

        return classificacaoRepository.save(classificacao);
    }

    @Override
    public List<PessoaClassificacao> listarClassificacoes(Long pessoaId) {
        return classificacaoRepository.findByPessoaId(pessoaId);
    }

    @Override
    @Transactional
    public void removerClassificacao(Long pessoaId, String tipo) {
        PessoaClassificacao classificacao = classificacaoRepository.findByPessoaIdAndTipo(pessoaId, tipo)
                .orElseThrow(() -> new ResourceNotFoundException("Classificação não encontrada para a pessoa: " + pessoaId + " e tipo: " + tipo));
        classificacaoRepository.delete(classificacao);
    }

    @Override
    @Transactional
    public PessoaClassificacao atualizarClassificacao(Long pessoaId, String tipo, PessoaClassificacao classificacaoAtualizada) {
        PessoaClassificacao classificacaoExistente = classificacaoRepository.findByPessoaIdAndTipo(pessoaId, tipo)
                .orElseThrow(() -> new ResourceNotFoundException("Classificação não encontrada para a pessoa: " + pessoaId + " e tipo: " + tipo));

        classificacaoExistente.setLimiteCredito(classificacaoAtualizada.getLimiteCredito());
        classificacaoExistente.setDiasPrazo(classificacaoAtualizada.getDiasPrazo());
        classificacaoExistente.setDescontoMaximo(classificacaoAtualizada.getDescontoMaximo());

        return classificacaoRepository.save(classificacaoExistente);
    }
}