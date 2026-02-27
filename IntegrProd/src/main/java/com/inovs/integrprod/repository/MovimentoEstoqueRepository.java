package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.MovimentoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long> {

    // Buscar movimentos por filial
    List<MovimentoEstoque> findByFilialId(Long filialId);

    // Buscar movimentos por produto
    List<MovimentoEstoque> findByProdutoId(Long produtoId);

    // Buscar movimentos por tipo
    List<MovimentoEstoque> findByTipoMovimento(String tipoMovimento);

    // Buscar movimentos por período
    List<MovimentoEstoque> findByDataMovimentoBetween(LocalDateTime inicio, LocalDateTime fim);

    // Buscar movimentos por documento
    List<MovimentoEstoque> findByDocumentoTipoAndDocumentoNumero(String documentoTipo, String documentoNumero);

    // Buscar movimentos de um lote específico
    @Query("SELECT m FROM MovimentoEstoque m WHERE m.loteOrigem = :lote OR m.loteDestino = :lote")
    List<MovimentoEstoque> findByLote(@Param("lote") String lote);

    // Buscar últimas movimentações de um produto
    @Query("SELECT m FROM MovimentoEstoque m WHERE m.produto.id = :produtoId ORDER BY m.dataMovimento DESC")
    List<MovimentoEstoque> findUltimosMovimentos(@Param("produtoId") Long produtoId);
}