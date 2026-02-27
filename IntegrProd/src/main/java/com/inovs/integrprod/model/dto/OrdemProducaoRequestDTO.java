package com.inovs.integrprod.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemProducaoRequestDTO {

    @NotNull(message = "Filial é obrigatória")
    private Long filialId;

    @NotBlank(message = "Número da OP é obrigatório")
    @Size(max = 30, message = "Número da OP deve ter no máximo 30 caracteres")
    private String numeroOp;

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    private String versaoEstrutura;

    @NotNull(message = "Quantidade planejada é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade planejada deve ser maior que zero")
    private BigDecimal quantidadePlanejada;

    private LocalDate dataEmissao;

    private LocalDate dataInicioPrevista;

    private LocalDate dataFimPrevista;

    @Min(value = 1, message = "Prioridade deve ser entre 1 e 10")
    @Max(value = 10, message = "Prioridade deve ser entre 1 e 10")
    private Integer prioridade = 5;

    private Long responsavelId;

    private String observacoes;

    @NotBlank(message = "Usuário de criação é obrigatório")
    private String usuarioCriacao;
}
