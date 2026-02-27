package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.PostoTrabalho;
import com.inovs.integrprod.repository.PostoTrabalhoRepository;
import com.inovs.integrprod.service.PostoTrabalhoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostoTrabalhoServiceImpl implements PostoTrabalhoService {

    private final PostoTrabalhoRepository postoTrabalhoRepository;

    @Override
    public PostoTrabalho salvar(PostoTrabalho postoTrabalho) {
        validarPostoTrabalho(postoTrabalho);

        if (postoTrabalhoRepository.existsByCodigo(postoTrabalho.getCodigo())) {
            throw new BusinessException("Já existe um posto de trabalho com o código: " + postoTrabalho.getCodigo());
        }

        if (postoTrabalho.getStatus() == null) {
            postoTrabalho.setStatus("ATIVO");
        }

        return postoTrabalhoRepository.save(postoTrabalho);
    }

    @Override
    public PostoTrabalho buscarPorId(Long id) {
        return postoTrabalhoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Posto de trabalho não encontrado com ID: " + id));
    }

    @Override
    public PostoTrabalho buscarPorCodigo(String codigo) {
        return postoTrabalhoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Posto de trabalho não encontrado com código: " + codigo));
    }

    @Override
    public List<PostoTrabalho> listarTodos() {
        return postoTrabalhoRepository.findAll();
    }

    @Override
    public List<PostoTrabalho> listarAtivos() {
        return postoTrabalhoRepository.findByStatus("ATIVO");
    }

    @Override
    public List<PostoTrabalho> buscarPorNome(String nome) {
        return postoTrabalhoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<PostoTrabalho> buscarPorSetor(String setor) {
        return postoTrabalhoRepository.findBySetor(setor);
    }

    @Override
    public List<PostoTrabalho> buscarPorStatus(String status) {
        return postoTrabalhoRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        PostoTrabalho postoTrabalho = buscarPorId(id);

        // Verificar se tem roteiros associados (quando implementarmos)
        // if (roteiroRepository.existsByPostoTrabalhoId(id)) {
        //     throw new BusinessException("Não é possível excluir posto com roteiros associados");
        // }

        // Ao invés de deletar, apenas inativar
        postoTrabalho.setStatus("INATIVO");
        postoTrabalhoRepository.save(postoTrabalho);
    }

    @Override
    @Transactional
    public PostoTrabalho atualizar(Long id, PostoTrabalho postoTrabalhoAtualizado) {
        PostoTrabalho postoTrabalhoExistente = buscarPorId(id);

        if (!postoTrabalhoExistente.getCodigo().equals(postoTrabalhoAtualizado.getCodigo()) &&
                postoTrabalhoRepository.existsByCodigo(postoTrabalhoAtualizado.getCodigo())) {
            throw new BusinessException("Já existe um posto de trabalho com o código: " + postoTrabalhoAtualizado.getCodigo());
        }

        postoTrabalhoExistente.setCodigo(postoTrabalhoAtualizado.getCodigo());
        postoTrabalhoExistente.setNome(postoTrabalhoAtualizado.getNome());
        postoTrabalhoExistente.setDescricao(postoTrabalhoAtualizado.getDescricao());
        postoTrabalhoExistente.setSetor(postoTrabalhoAtualizado.getSetor());
        postoTrabalhoExistente.setCustoHora(postoTrabalhoAtualizado.getCustoHora());
        postoTrabalhoExistente.setCustoFixoHora(postoTrabalhoAtualizado.getCustoFixoHora());
        postoTrabalhoExistente.setStatus(postoTrabalhoAtualizado.getStatus());

        return postoTrabalhoRepository.save(postoTrabalhoExistente);
    }

    @Override
    @Transactional
    public PostoTrabalho atualizarCusto(Long id, BigDecimal custoHora, BigDecimal custoFixoHora) {
        PostoTrabalho postoTrabalho = buscarPorId(id);
        postoTrabalho.setCustoHora(custoHora);
        postoTrabalho.setCustoFixoHora(custoFixoHora);
        return postoTrabalhoRepository.save(postoTrabalho);
    }

    @Override
    public void validarPostoTrabalho(PostoTrabalho postoTrabalho) {
        if (postoTrabalho.getCodigo() == null || postoTrabalho.getCodigo().trim().isEmpty()) {
            throw new BusinessException("Código do posto de trabalho é obrigatório");
        }

        if (postoTrabalho.getNome() == null || postoTrabalho.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome do posto de trabalho é obrigatório");
        }

        if (postoTrabalho.getCustoHora() != null && postoTrabalho.getCustoHora().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Custo hora não pode ser negativo");
        }

        if (postoTrabalho.getCustoFixoHora() != null && postoTrabalho.getCustoFixoHora().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Custo fixo hora não pode ser negativo");
        }
    }
}