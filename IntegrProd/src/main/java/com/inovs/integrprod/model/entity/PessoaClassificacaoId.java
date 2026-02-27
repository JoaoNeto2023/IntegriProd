package com.inovs.integrprod.model.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class PessoaClassificacaoId implements Serializable {
    private Long pessoa;
    private String tipo;
}