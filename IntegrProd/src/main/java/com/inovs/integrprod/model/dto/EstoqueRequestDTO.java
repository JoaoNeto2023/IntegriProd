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
public class EstoqueRequestDTO {

    @NotNull(message = "Filial é obrigatória")
    private Long filialId;

    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    private String lote;

    private LocalDate dataFabricacao;

    private LocalDate dataValidade;

    @NotNull(message = "Quantidade é obrigatória")
    @DecimalMin(value = "0.0", message = "Quantidade deve ser maior ou igual a zero")
    private BigDecimal quantidade;

    private BigDecimal reservado = BigDecimal.ZERO;

    @Size(max = 50, message = "Localização deve ter no máximo 50 caracteres")
    private String localizacao;

    @DecimalMin(value = "0.0", message = "Custo unitário deve ser maior ou igual a zero")
    private BigDecimal custoUnitario;

    private String statusLote;

    private String documentoEntrada;
}