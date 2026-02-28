package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.ConsumoMaterial;
import java.util.List;

public interface ConsumoMaterialService {

    ConsumoMaterial salvar(ConsumoMaterial consumo);

    ConsumoMaterial buscarPorId(Long id);

    List<ConsumoMaterial> listarTodos();

    List<ConsumoMaterial> buscarPorOrdem(Long ordemId);

    List<ConsumoMaterial> buscarPorProduto(Long produtoId);

    void deletar(Long id);

    ConsumoMaterial atualizar(Long id, ConsumoMaterial consumo);

    void validarConsumo(ConsumoMaterial consumo);
}