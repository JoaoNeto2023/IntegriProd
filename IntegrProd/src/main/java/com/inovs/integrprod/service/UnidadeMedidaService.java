package com.inovs.integrprod.service;



import com.inovs.integrprod.model.entity.UnidadeMedida;
import java.util.List;

public interface UnidadeMedidaService {

    UnidadeMedida salvar(UnidadeMedida unidadeMedida);

    UnidadeMedida buscarPorSigla(String sigla);

    List<UnidadeMedida> listarTodas();

    List<UnidadeMedida> buscarPorDescricao(String descricao);

    void deletar(String sigla);

    UnidadeMedida atualizar(String sigla, UnidadeMedida unidadeMedida);
}