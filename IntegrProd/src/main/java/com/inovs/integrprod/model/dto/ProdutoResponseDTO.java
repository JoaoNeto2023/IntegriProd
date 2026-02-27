package com.inovs.integrprod.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {

    private Long id;
    private String codigo;
    private String codigoBarras;
    private String nome;
    private String descricao;
    private Long tipoProdutoId;
    private String tipoProdutoDescricao;
    private Long familiaId;
    private String familiaNome;
    private String unidadeMedidaSigla;
    private String unidadeMedidaDescricao;
    private String ncm;
    private String cest;
    private String origem;
    private String tipoItem;
    private BigDecimal pesoBruto;
    private BigDecimal pesoLiquido;
    private Boolean controlaLote;
    private Boolean controlaValidade;
    private Integer diasValidade;
    private BigDecimal estoqueMinimo;
    private BigDecimal estoqueMaximo;
    private BigDecimal pontoPedido;
    private BigDecimal custoUnitario;
    private BigDecimal custoUltimaCompra;
    private BigDecimal custoMedio;
    private BigDecimal markupPercentual;
    private BigDecimal precoVenda;
    private String status;
    private LocalDateTime dataCadastro;
    private String usuarioCadastro;
}