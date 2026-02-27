package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.FamiliaProduto;
import com.inovs.integrprod.repository.FamiliaProdutoRepository;
import com.inovs.integrprod.service.FamiliaProdutoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FamiliaProdutoServiceImpl implements FamiliaProdutoService {

    private final FamiliaProdutoRepository familiaProdutoRepository;

    @Override
    public FamiliaProduto salvar(FamiliaProduto familiaProduto) {
        validarFamilia(familiaProduto);

        if (familiaProdutoRepository.existsByCodigo(familiaProduto.getCodigo())) {
            throw new BusinessException("Já existe uma família com o código: " + familiaProduto.getCodigo());
        }

        // Se tiver pai, verificar se existe
        if (familiaProduto.getPai() != null && familiaProduto.getPai().getId() != null) {
            FamiliaProduto pai = buscarPorId(familiaProduto.getPai().getId());
            familiaProduto.setPai(pai);

            // Verificar se não está criando ciclo (pai não pode ser filho de si mesmo)
            if (verificarCiclo(familiaProduto)) {
                throw new BusinessException("Não é possível criar um ciclo na hierarquia de famílias");
            }
        }

        return familiaProdutoRepository.save(familiaProduto);
    }

    @Override
    public FamiliaProduto buscarPorId(Long id) {
        return familiaProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Família não encontrada com ID: " + id));
    }

    @Override
    public FamiliaProduto buscarPorCodigo(String codigo) {
        return familiaProdutoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Família não encontrada com código: " + codigo));
    }

    @Override
    public List<FamiliaProduto> listarTodas() {
        return familiaProdutoRepository.findAll();
    }

    @Override
    public List<FamiliaProduto> listarFamiliasRaiz() {
        return familiaProdutoRepository.findFamiliasRaiz();
    }

    @Override
    public List<FamiliaProduto> buscarSubfamilias(Long paiId) {
        return familiaProdutoRepository.findSubfamilias(paiId);
    }

    @Override
    public List<FamiliaProduto> buscarPorNome(String nome) {
        return familiaProdutoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        FamiliaProduto familia = buscarPorId(id);

        // Verificar se tem subfamílias
        if (familia.temSubfamilias()) {
            throw new BusinessException("Não é possível excluir uma família que possui subfamílias");
        }

        // Verificar se tem produtos associados (irei implementar depois)
        // if (produtoRepository.existsByFamiliaId(id)) {
        //     throw new BusinessException("Não é possível excluir uma família com produtos associados");
        // }

        familiaProdutoRepository.delete(familia);
    }

    @Override
    @Transactional
    public FamiliaProduto atualizar(Long id, FamiliaProduto familiaAtualizada) {
        FamiliaProduto familiaExistente = buscarPorId(id);

        if (!familiaExistente.getCodigo().equals(familiaAtualizada.getCodigo()) &&
                familiaProdutoRepository.existsByCodigo(familiaAtualizada.getCodigo())) {
            throw new BusinessException("Já existe uma família com o código: " + familiaAtualizada.getCodigo());
        }

        familiaExistente.setCodigo(familiaAtualizada.getCodigo());
        familiaExistente.setNome(familiaAtualizada.getNome());
        familiaExistente.setDescricao(familiaAtualizada.getDescricao());

        // Atualizar pai se necessário
        if (familiaAtualizada.getPai() != null) {
            FamiliaProduto novoPai = buscarPorId(familiaAtualizada.getPai().getId());

            // Verificar ciclo
            if (novoPai.getId().equals(id)) {
                throw new BusinessException("Uma família não pode ser pai dela mesma");
            }

            familiaExistente.setPai(novoPai);
        } else {
            familiaExistente.setPai(null);
        }

        return familiaProdutoRepository.save(familiaExistente);
    }

    @Override
    public void validarFamilia(FamiliaProduto familiaProduto) {
        if (familiaProduto.getCodigo() == null || familiaProduto.getCodigo().trim().isEmpty()) {
            throw new BusinessException("Código da família é obrigatório");
        }

        if (familiaProduto.getNome() == null || familiaProduto.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome da família é obrigatório");
        }
    }

    private boolean verificarCiclo(FamiliaProduto familia) {
        if (familia.getPai() == null) return false;

        FamiliaProduto atual = familia.getPai();
        while (atual != null) {
            if (atual.getId().equals(familia.getId())) {
                return true;
            }
            atual = atual.getPai();
        }
        return false;
    }
}