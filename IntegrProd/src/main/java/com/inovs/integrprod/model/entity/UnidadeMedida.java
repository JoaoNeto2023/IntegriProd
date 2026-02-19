package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "unidade_medida")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeMedida {

    @Id
    @Column(name = "sigla", length = 10)
    private String sigla;

    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
}