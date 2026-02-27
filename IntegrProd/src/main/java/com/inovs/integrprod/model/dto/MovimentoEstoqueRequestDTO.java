package com.inovs.integrprod.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoqueRequestDTO {

    @NotNull(message = "Filial é obrigatória")
    private Long filialId;

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    private Long estoqueId;

    @NotBlank(message = "Tipo de movimento é obrigatório")
    private String tipoMovimento;

    private String documentoTipo;

    private String documentoNumero;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.01", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantidade;

    @DecimalMin(value = "0.0", message = "Valor unitário deve ser maior ou igual a zero")
    private BigDecimal valorUnitario;

    private String loteOrigem;

    private String loteDestino;

    @NotBlank(message = "Usuário é obrigatório")
    private String usuarioMovimento;

    private String observacao;
}