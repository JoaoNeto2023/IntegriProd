package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.Produto;
import com.inovs.integrprod.model.entity.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar por código exato
    Optional<Produto> findByCodigo(String codigo);

    // Verificar se código existe
    boolean existsByCodigo(String codigo);

    // Buscar por nome (contém, ignorando maiúsculas/minúsculas)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Buscar por tipo de produto
    List<Produto> findByTipoProduto(TipoProduto tipoProduto);

    // Buscar por status
    List<Produto> findByStatus(String status);

    // Buscar produtos com estoque baixo (personalizada com JPQL)
    @Query("SELECT p FROM Produto p WHERE p.estoqueMinimo IS NOT NULL")
    List<Produto> findProdutosComEstoqueMinimo();

    // Buscar produtos com preço de venda entre dois valores
    List<Produto> findByPrecoVendaBetween(BigDecimal min, BigDecimal max);

    // Buscar produtos por família
    @Query("SELECT p FROM Produto p WHERE p.familia.id = :familiaId")
    List<Produto> findByFamiliaId(@Param("familiaId") Long familiaId);

    // Buscar produtos ativos
    @Query("SELECT p FROM Produto p WHERE p.status = 'ATIVO'")
    List<Produto> findAllAtivos();
}
