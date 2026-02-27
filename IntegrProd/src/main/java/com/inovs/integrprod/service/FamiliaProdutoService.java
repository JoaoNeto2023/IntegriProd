package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.FamiliaProduto;
import java.util.List;

public interface FamiliaProdutoService {

    FamiliaProduto salvar(FamiliaProduto familiaProduto);

    FamiliaProduto buscarPorId(Long id);

    FamiliaProduto buscarPorCodigo(String codigo);

    List<FamiliaProduto> listarTodas();

    List<FamiliaProduto> listarFamiliasRaiz();

    List<FamiliaProduto> buscarSubfamilias(Long paiId);

    List<FamiliaProduto> buscarPorNome(String nome);

    void deletar(Long id);

    FamiliaProduto atualizar(Long id, FamiliaProduto familiaProduto);

    void validarFamilia(FamiliaProduto familiaProduto);
}