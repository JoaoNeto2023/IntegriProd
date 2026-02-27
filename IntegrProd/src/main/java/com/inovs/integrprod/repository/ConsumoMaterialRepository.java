package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.ConsumoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ConsumoMaterialRepository extends JpaRepository<ConsumoMaterial, Long> {

    List<ConsumoMaterial> findByOrdemProducaoId(Long ordemProducaoId);

    List<ConsumoMaterial> findByProdutoId(Long produtoId);

    @Query("SELECT SUM(c.valorTotal) FROM ConsumoMaterial c WHERE c.ordemProducao.id = :ordemId")
    BigDecimal sumValorTotalByOrdemId(@Param("ordemId") Long ordemId);

    @Query("SELECT c FROM ConsumoMaterial c WHERE c.ordemProducao.id = :ordemId AND c.produto.id = :produtoId")
    List<ConsumoMaterial> findByOrdemAndProduto(@Param("ordemId") Long ordemId, @Param("produtoId") Long produtoId);
}