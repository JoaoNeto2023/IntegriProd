package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.ControleQualidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ControleQualidadeRepository extends JpaRepository<ControleQualidade, Long> {

    List<ControleQualidade> findByOrdemProducaoId(Long ordemProducaoId);

    List<ControleQualidade> findByProdutoId(Long produtoId);

    List<ControleQualidade> findByResultado(String resultado);

    List<ControleQualidade> findByLote(String lote);

    @Query("SELECT c FROM ControleQualidade c WHERE c.ordemProducao.id = :ordemId AND c.resultado = 'REPROVADO'")
    List<ControleQualidade> findReprovadosByOrdemId(@Param("ordemId") Long ordemId);

    @Query("SELECT AVG(c.quantidadeAprovada / c.quantidadeInspecionada) * 100 FROM ControleQualidade c WHERE c.produto.id = :produtoId")
    Double calcularIndiceQualidade(@Param("produtoId") Long produtoId);
}