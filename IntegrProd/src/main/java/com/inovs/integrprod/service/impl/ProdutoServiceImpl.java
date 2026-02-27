package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.Produto;
import com.inovs.integrprod.model.entity.TipoProduto;
import com.inovs.integrprod.model.entity.UnidadeMedida;
import com.inovs.integrprod.repository.ProdutoRepository;
import com.inovs.integrprod.repository.TipoProdutoRepository;
import com.inovs.integrprod.repository.UnidadeMedidaRepository;
import com.inovs.integrprod.service.ProdutoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final TipoProdutoRepository tipoProdutoRepository;
    private final UnidadeMedidaRepository unidadeMedidaRepository;

    @Override
    public Produto salvar(Produto produto) {
        validarProduto(produto);

        // Verificar se código já existe
        if (produtoRepository.existsByCodigo(produto.getCodigo())) {
            throw new BusinessException("Já existe um produto com o código: " + produto.getCodigo());
        }

        // Se não informou status, colocar como ATIVO
        if (produto.getStatus() == null || produto.getStatus().isEmpty()) {
            produto.setStatus("ATIVO");
        }

        // Calcular preço de venda se tiver markup e custo
        if (produto.getMarkupPercentual() != null && produto.getCustoUnitario() != null) {
            calcularPrecoVenda(produto);
        }

        return produtoRepository.save(produto);
    }

    @Override
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    @Override
    public Produto buscarPorCodigo(String codigo) {
        return produtoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com código: " + codigo));
    }

    @Override
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Override
    public List<Produto> listarAtivos() {
        return produtoRepository.findAllAtivos();
    }

    @Override
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Produto> buscarPorTipoProduto(Long tipoProdutoId) {
        TipoProduto tipoProduto = tipoProdutoRepository.findById(tipoProdutoId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de produto não encontrado"));
        return produtoRepository.findByTipoProduto(tipoProduto);
    }

    @Override
    public List<Produto> buscarPorFamilia(Long familiaId) {
        return produtoRepository.findByFamiliaId(familiaId);
    }

    @Override
    public List<Produto> buscarPorPrecoEntre(BigDecimal min, BigDecimal max) {
        return produtoRepository.findByPrecoVendaBetween(min, max);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Produto produto = buscarPorId(id);

        // Verificar se pode deletar (não ter movimentações, etc)
        // Por segurança, apenas inativamos o produto
        produto.setStatus("INATIVO");
        produtoRepository.save(produto);

        // Se realmente quiser deletar fisicamente:
        // produtoRepository.delete(produto);
    }

    @Override
    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(id);

        // Verificar se o código está sendo alterado para um que já existe
        if (!produtoExistente.getCodigo().equals(produtoAtualizado.getCodigo()) &&
                produtoRepository.existsByCodigo(produtoAtualizado.getCodigo())) {
            throw new BusinessException("Já existe um produto com o código: " + produtoAtualizado.getCodigo());
        }

        // Atualizar campos permitidos
        produtoExistente.setCodigo(produtoAtualizado.getCodigo());
        produtoExistente.setCodigoBarras(produtoAtualizado.getCodigoBarras());
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setTipoProduto(produtoAtualizado.getTipoProduto());
        produtoExistente.setFamilia(produtoAtualizado.getFamilia());
        produtoExistente.setUnidadeMedida(produtoAtualizado.getUnidadeMedida());
        produtoExistente.setNcm(produtoAtualizado.getNcm());
        produtoExistente.setCest(produtoAtualizado.getCest());
        produtoExistente.setPesoBruto(produtoAtualizado.getPesoBruto());
        produtoExistente.setPesoLiquido(produtoAtualizado.getPesoLiquido());
        produtoExistente.setControlaLote(produtoAtualizado.getControlaLote());
        produtoExistente.setControlaValidade(produtoAtualizado.getControlaValidade());
        produtoExistente.setDiasValidade(produtoAtualizado.getDiasValidade());
        produtoExistente.setEstoqueMinimo(produtoAtualizado.getEstoqueMinimo());
        produtoExistente.setEstoqueMaximo(produtoAtualizado.getEstoqueMaximo());
        produtoExistente.setPontoPedido(produtoAtualizado.getPontoPedido());
        produtoExistente.setCustoUnitario(produtoAtualizado.getCustoUnitario());
        produtoExistente.setMarkupPercentual(produtoAtualizado.getMarkupPercentual());
        produtoExistente.setUsuarioCadastro(produtoAtualizado.getUsuarioCadastro());

        // Recalcular preço se necessário
        if (produtoExistente.getMarkupPercentual() != null && produtoExistente.getCustoUnitario() != null) {
            calcularPrecoVenda(produtoExistente);
        }

        return produtoRepository.save(produtoExistente);
    }

    @Override
    @Transactional
    public Produto atualizarPreco(Long id, BigDecimal novoPreco) {
        Produto produto = buscarPorId(id);
        produto.setPrecoVenda(novoPreco);
        return produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public Produto atualizarStatus(Long id, String status) {
        Produto produto = buscarPorId(id);
        produto.setStatus(status);
        return produtoRepository.save(produto);
    }

    @Override
    public void validarProduto(Produto produto) {
        if (produto.getCodigo() == null || produto.getCodigo().trim().isEmpty()) {
            throw new BusinessException("Código do produto é obrigatório");
        }

        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome do produto é obrigatório");
        }

        if (produto.getTipoProduto() == null) {
            throw new BusinessException("Tipo de produto é obrigatório");
        }

        if (produto.getUnidadeMedida() == null) {
            throw new BusinessException("Unidade de medida é obrigatória");
        }

        // Validar pesos
        if (produto.getPesoBruto() != null && produto.getPesoLiquido() != null) {
            if (produto.getPesoBruto().compareTo(produto.getPesoLiquido()) < 0) {
                throw new BusinessException("Peso bruto não pode ser menor que peso líquido");
            }
        }

        // Validar markup e preço
        if (produto.getMarkupPercentual() != null && produto.getCustoUnitario() != null) {
            if (produto.getMarkupPercentual().compareTo(BigDecimal.ZERO) < 0) {
                throw new BusinessException("Markup percentual não pode ser negativo");
            }
        }
    }

    private void calcularPrecoVenda(Produto produto) {
        // Preço = Custo + (Custo * Markup/100)
        BigDecimal markup = produto.getMarkupPercentual()
                .divide(BigDecimal.valueOf(100));
        BigDecimal acrescimo = produto.getCustoUnitario().multiply(markup);
        BigDecimal precoCalculado = produto.getCustoUnitario().add(acrescimo);
        produto.setPrecoVenda(precoCalculado);
    }
}
