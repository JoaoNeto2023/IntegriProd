package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.PostoTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostoTrabalhoRepository extends JpaRepository<PostoTrabalho, Long> {

    Optional<PostoTrabalho> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<PostoTrabalho> findByNomeContainingIgnoreCase(String nome);

    List<PostoTrabalho> findBySetor(String setor);

    List<PostoTrabalho> findByStatus(String status);

    @Query("SELECT p FROM PostoTrabalho p WHERE p.custoHora IS NOT NULL")
    List<PostoTrabalho> findPostosComCustoDefinido();

    @Query("SELECT p FROM PostoTrabalho p WHERE p.setor = :setor AND p.status = 'ATIVO'")
    List<PostoTrabalho> findAtivosPorSetor(@Param("setor") String setor);
}