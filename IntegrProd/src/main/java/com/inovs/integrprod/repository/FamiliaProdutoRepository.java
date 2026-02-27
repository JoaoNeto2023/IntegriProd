package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.FamiliaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FamiliaProdutoRepository extends JpaRepository<FamiliaProduto, Long> {

    Optional<FamiliaProduto> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<FamiliaProduto> findByNomeContainingIgnoreCase(String nome);

    // Buscar famílias que não têm pai (raízes)
    @Query("SELECT f FROM FamiliaProduto f WHERE f.pai IS NULL")
    List<FamiliaProduto> findFamiliasRaiz();

    // Buscar subfamílias de uma família específica
    @Query("SELECT f FROM FamiliaProduto f WHERE f.pai.id = :paiId")
    List<FamiliaProduto> findSubfamilias(@Param("paiId") Long paiId);

    // Buscar toda a árvore de uma família
    @Query("SELECT f FROM FamiliaProduto f WHERE f.pai.id = :id OR f.id = :id")
    List<FamiliaProduto> findArvoreFamiliar(@Param("id") Long id);
}