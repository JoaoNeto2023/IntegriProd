package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "familia_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FamiliaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 20)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "pai_id")
    @JsonIgnore
    private FamiliaProduto pai;

    @OneToMany(mappedBy = "pai", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FamiliaProduto> subfamilias;

    // Método auxiliar para verificar se tem subfamílias
    public boolean temSubfamilias() {
        return subfamilias != null && !subfamilias.isEmpty();
    }
}