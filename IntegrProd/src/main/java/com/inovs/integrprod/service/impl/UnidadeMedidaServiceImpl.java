package com.inovs.integrprod.service.impl;


import com.inovs.integrprod.model.entity.UnidadeMedida;
import com.inovs.integrprod.repository.UnidadeMedidaRepository;
import com.inovs.integrprod.service.UnidadeMedidaService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UnidadeMedidaServiceImpl implements UnidadeMedidaService {

    private final UnidadeMedidaRepository unidadeMedidaRepository;

    @Override
    public UnidadeMedida salvar(UnidadeMedida unidadeMedida) {
        // Verificar se já existe uma unidade com a mesma sigla
        if (unidadeMedidaRepository.existsById(unidadeMedida.getSigla())) {
            throw new BusinessException("Já existe uma unidade de medida com a sigla: " + unidadeMedida.getSigla());
        }
        return unidadeMedidaRepository.save(unidadeMedida);
    }

    @Override
    public UnidadeMedida buscarPorSigla(String sigla) {
        return unidadeMedidaRepository.findById(sigla)
                .orElseThrow(() -> new ResourceNotFoundException("Unidade de medida não encontrada com sigla: " + sigla));
    }

    @Override
    public List<UnidadeMedida> listarTodas() {
        return unidadeMedidaRepository.findAll();
    }

    @Override
    public List<UnidadeMedida> buscarPorDescricao(String descricao) {
        return unidadeMedidaRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Override
    @Transactional
    public void deletar(String sigla) {
        UnidadeMedida unidadeMedida = buscarPorSigla(sigla);
        unidadeMedidaRepository.delete(unidadeMedida);
    }

    @Override
    @Transactional
    public UnidadeMedida atualizar(String sigla, UnidadeMedida unidadeMedidaAtualizada) {
        // Verificar se a unidade existe
        UnidadeMedida unidadeExistente = buscarPorSigla(sigla);

        // Atualizar apenas a descrição (a sigla não pode ser alterada)
        unidadeExistente.setDescricao(unidadeMedidaAtualizada.getDescricao());

        return unidadeMedidaRepository.save(unidadeExistente);
    }
}