package com.inovs.integrprod.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {

    @NotBlank(message = "Código é obrigatório")
    @Size(max = 30, message = "Código deve ter no máximo 30 caracteres")
    private String codigo;

    @Size(max = 50, message = "Código de barras deve ter no máximo 50 caracteres")
    private String codigoBarras;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    private String nome;

    private String descricao;

    @NotNull(message = "Tipo de produto é obrigatório")
    private Long tipoProdutoId;

    private Long familiaId;

    @NotBlank(message = "Unidade de medida é obrigatória")
    private String unidadeMedidaSigla;

    private String ncm;

    private String cest;

    private String origem;

    private String tipoItem;

    @DecimalMin(value = "0.0", message = "Peso bruto deve ser maior ou igual a zero")
    private BigDecimal pesoBruto;

    @DecimalMin(value = "0.0", message = "Peso líquido deve ser maior ou igual a zero")
    private BigDecimal pesoLiquido;

    private Boolean controlaLote = false;

    private Boolean controlaValidade = false;

    @Min(value = 1, message = "Dias de validade deve ser no mínimo 1")
    private Integer diasValidade;

    @DecimalMin(value = "0.0", message = "Estoque mínimo deve ser maior ou igual a zero")
    private BigDecimal estoqueMinimo;

    @DecimalMin(value = "0.0", message = "Estoque máximo deve ser maior ou igual a zero")
    private BigDecimal estoqueMaximo;

    @DecimalMin(value = "0.0", message = "Ponto de pedido deve ser maior ou igual a zero")
    private BigDecimal pontoPedido;

    @DecimalMin(value = "0.0", message = "Custo unitário deve ser maior ou igual a zero")
    private BigDecimal custoUnitario;

    @DecimalMin(value = "0.0", message = "Markup percentual deve ser maior ou igual a zero")
    @DecimalMax(value = "100.0", message = "Markup percentual deve ser no máximo 100")
    private BigDecimal markupPercentual;

    @DecimalMin(value = "0.0", message = "Preço de venda deve ser maior ou igual a zero")
    private BigDecimal precoVenda;

    private String status;

    private String usuarioCadastro;
}