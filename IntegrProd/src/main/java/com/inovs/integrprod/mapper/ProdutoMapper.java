package com.inovs.integrprod.mapper;

import com.inovs.integrprod.model.entity.Produto;
import com.inovs.integrprod.model.entity.TipoProduto;
import com.inovs.integrprod.model.entity.FamiliaProduto;
import com.inovs.integrprod.model.entity.UnidadeMedida;
import com.inovs.integrprod.model.dto.ProdutoRequestDTO;
import com.inovs.integrprod.model.dto.ProdutoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoRequestDTO dto,
                            TipoProduto tipoProduto,
                            FamiliaProduto familia,
                            UnidadeMedida unidadeMedida) {
        Produto produto = new Produto();
        produto.setCodigo(dto.getCodigo());
        produto.setCodigoBarras(dto.getCodigoBarras());
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setTipoProduto(tipoProduto);
        produto.setFamilia(familia);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setNcm(dto.getNcm());
        produto.setCest(dto.getCest());
        produto.setOrigem(dto.getOrigem());
        produto.setTipoItem(dto.getTipoItem());
        produto.setPesoBruto(dto.getPesoBruto());
        produto.setPesoLiquido(dto.getPesoLiquido());
        produto.setControlaLote(dto.getControlaLote());
        produto.setControlaValidade(dto.getControlaValidade());
        produto.setDiasValidade(dto.getDiasValidade());
        produto.setEstoqueMinimo(dto.getEstoqueMinimo());
        produto.setEstoqueMaximo(dto.getEstoqueMaximo());
        produto.setPontoPedido(dto.getPontoPedido());
        produto.setCustoUnitario(dto.getCustoUnitario());
        produto.setMarkupPercentual(dto.getMarkupPercentual());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setStatus(dto.getStatus());
        produto.setUsuarioCadastro(dto.getUsuarioCadastro());
        return produto;
    }

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setCodigo(produto.getCodigo());
        dto.setCodigoBarras(produto.getCodigoBarras());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());

        if (produto.getTipoProduto() != null) {
            dto.setTipoProdutoId(produto.getTipoProduto().getId());
            dto.setTipoProdutoDescricao(produto.getTipoProduto().getDescricao());
        }

        if (produto.getFamilia() != null) {
            dto.setFamiliaId(produto.getFamilia().getId());
            dto.setFamiliaNome(produto.getFamilia().getNome());
        }

        if (produto.getUnidadeMedida() != null) {
            dto.setUnidadeMedidaSigla(produto.getUnidadeMedida().getSigla());
            dto.setUnidadeMedidaDescricao(produto.getUnidadeMedida().getDescricao());
        }

        dto.setNcm(produto.getNcm());
        dto.setCest(produto.getCest());
        dto.setOrigem(produto.getOrigem());
        dto.setTipoItem(produto.getTipoItem());
        dto.setPesoBruto(produto.getPesoBruto());
        dto.setPesoLiquido(produto.getPesoLiquido());
        dto.setControlaLote(produto.getControlaLote());
        dto.setControlaValidade(produto.getControlaValidade());
        dto.setDiasValidade(produto.getDiasValidade());
        dto.setEstoqueMinimo(produto.getEstoqueMinimo());
        dto.setEstoqueMaximo(produto.getEstoqueMaximo());
        dto.setPontoPedido(produto.getPontoPedido());
        dto.setCustoUnitario(produto.getCustoUnitario());
        dto.setCustoUltimaCompra(produto.getCustoUltimaCompra());
        dto.setCustoMedio(produto.getCustoMedio());
        dto.setMarkupPercentual(produto.getMarkupPercentual());
        dto.setPrecoVenda(produto.getPrecoVenda());
        dto.setStatus(produto.getStatus());
        dto.setDataCadastro(produto.getDataCadastro());
        dto.setUsuarioCadastro(produto.getUsuarioCadastro());

        return dto;
    }

    public void updateEntityFromDTO(ProdutoRequestDTO dto, Produto produto) {
        if (dto.getCodigo() != null) produto.setCodigo(dto.getCodigo());
        if (dto.getCodigoBarras() != null) produto.setCodigoBarras(dto.getCodigoBarras());
        if (dto.getNome() != null) produto.setNome(dto.getNome());
        if (dto.getDescricao() != null) produto.setDescricao(dto.getDescricao());
        if (dto.getNcm() != null) produto.setNcm(dto.getNcm());
        if (dto.getCest() != null) produto.setCest(dto.getCest());
        if (dto.getOrigem() != null) produto.setOrigem(dto.getOrigem());
        if (dto.getTipoItem() != null) produto.setTipoItem(dto.getTipoItem());
        if (dto.getPesoBruto() != null) produto.setPesoBruto(dto.getPesoBruto());
        if (dto.getPesoLiquido() != null) produto.setPesoLiquido(dto.getPesoLiquido());
        if (dto.getControlaLote() != null) produto.setControlaLote(dto.getControlaLote());
        if (dto.getControlaValidade() != null) produto.setControlaValidade(dto.getControlaValidade());
        if (dto.getDiasValidade() != null) produto.setDiasValidade(dto.getDiasValidade());
        if (dto.getEstoqueMinimo() != null) produto.setEstoqueMinimo(dto.getEstoqueMinimo());
        if (dto.getEstoqueMaximo() != null) produto.setEstoqueMaximo(dto.getEstoqueMaximo());
        if (dto.getPontoPedido() != null) produto.setPontoPedido(dto.getPontoPedido());
        if (dto.getCustoUnitario() != null) produto.setCustoUnitario(dto.getCustoUnitario());
        if (dto.getMarkupPercentual() != null) produto.setMarkupPercentual(dto.getMarkupPercentual());
        if (dto.getPrecoVenda() != null) produto.setPrecoVenda(dto.getPrecoVenda());
        if (dto.getStatus() != null) produto.setStatus(dto.getStatus());
    }
}