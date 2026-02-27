package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "posto_trabalho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostoTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 20)
    private String codigo;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "setor", length = 50)
    private String setor;

    @Column(name = "custo_hora", precision = 15, scale = 2)
    private BigDecimal custoHora;

    @Column(name = "custo_fixo_hora", precision = 15, scale = 2)
    private BigDecimal custoFixoHora;

    @Column(name = "status", length = 20)
    private String status = "ATIVO";
}