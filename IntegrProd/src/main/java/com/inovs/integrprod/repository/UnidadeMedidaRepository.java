package com.inovs.integrprod.repository;


import com.inovs.integrprod.model.entity.UnidadeMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, String> {

    // Buscar por descrição
    List<UnidadeMedida> findByDescricaoContainingIgnoreCase(String descricao);

    // Verificar se existe por sigla
    boolean existsBySigla(String sigla);
}
