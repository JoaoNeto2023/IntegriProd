package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long> {

    Optional<TipoProduto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    List<TipoProduto> findByNatureza(String natureza);
}