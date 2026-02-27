package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.Estoque;
import com.inovs.integrprod.model.entity.Filial;
import com.inovs.integrprod.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    // Buscar por filial
    List<Estoque> findByFilialId(Long filialId);

    // Buscar por produto
    List<Estoque> findByProdutoId(Long produtoId);

    // Buscar por filial e produto
    List<Estoque> findByFilialIdAndProdutoId(Long filialId, Long produtoId);

    // Buscar por lote específico
    Optional<Estoque> findByFilialIdAndProdutoIdAndLote(Long filialId, Long produtoId, String lote);

    // Buscar produtos próximos ao vencimento
    @Query("SELECT e FROM Estoque e WHERE e.dataValidade BETWEEN :inicio AND :fim AND e.quantidade > 0")
    List<Estoque> findProdutosAVencer(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    // Buscar produtos vencidos
    @Query("SELECT e FROM Estoque e WHERE e.dataValidade < :data AND e.quantidade > 0")
    List<Estoque> findProdutosVencidos(@Param("data") LocalDate data);

    // Buscar produtos com estoque baixo (abaixo do mínimo)
    @Query("SELECT e FROM Estoque e WHERE e.quantidade <= e.produto.estoqueMinimo AND e.quantidade > 0")
    List<Estoque> findEstoqueAbaixoMinimo();

    // Buscar por status do lote
    List<Estoque> findByStatusLote(String statusLote);

    // Buscar por localização
    List<Estoque> findByLocalizacaoContainingIgnoreCase(String localizacao);

    // Calcular quantidade total de um produto em uma filial
    @Query("SELECT SUM(e.quantidade) FROM Estoque e WHERE e.filial.id = :filialId AND e.produto.id = :produtoId")
    BigDecimal sumQuantidadeByFilialAndProduto(@Param("filialId") Long filialId, @Param("produtoId") Long produtoId);

    // Verificar se existe lote para um produto
    boolean existsByProdutoIdAndLoteIsNotNull(Long produtoId);
}