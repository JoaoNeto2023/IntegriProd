package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.PessoaEndereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, Long> {

    List<PessoaEndereco> findByPessoaId(Long pessoaId);

    List<PessoaEndereco> findByPessoaIdAndTipoEndereco(Long pessoaId, String tipoEndereco);

    @Query("SELECT e FROM PessoaEndereco e WHERE e.pessoa.id = :pessoaId AND e.principal = true")
    Optional<PessoaEndereco> findEnderecoPrincipal(@Param("pessoaId") Long pessoaId);

    @Query("SELECT e FROM PessoaEndereco e WHERE e.cidade = :cidade")
    List<PessoaEndereco> findByCidade(@Param("cidade") String cidade);

    @Query("SELECT e FROM PessoaEndereco e WHERE e.uf = :uf")
    List<PessoaEndereco> findByUf(@Param("uf") String uf);

    boolean existsByPessoaIdAndPrincipalTrue(Long pessoaId);

    @Modifying
    @Query("UPDATE PessoaEndereco e SET e.principal = false WHERE e.pessoa.id = :pessoaId")
    void resetarEnderecoPrincipal(@Param("pessoaId") Long pessoaId);
}