package com.back.SteelTech.Service;

import com.back.SteelTech.DTO.ProdutoEntradaDTO;
import com.back.SteelTech.DTO.ProdutoSaidaDTO;
import com.back.SteelTech.Entity.Notificacao;
import com.back.SteelTech.Entity.Produto;
import com.back.SteelTech.Repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final List<ObserverProduto> observador;
    private final ProdutoRepository produtoRepository;

    // LISTAR TODOS OS PRODUTOS
    public ResponseEntity<List<ProdutoSaidaDTO>> listarTodosProdutos() {
        List<ProdutoSaidaDTO> produtos = produtoRepository.findAll()
                .stream()
                .map(this::converterParaSaidaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    public ResponseEntity<String> cadastrarProduto(ProdutoEntradaDTO dto) {
        produtoRepository.save(converterParaEntidade(dto));
        return ResponseEntity.ok("Produto cadastrado com sucesso.");
    }

    public ResponseEntity<String> atualizarProduto(Integer id, ProdutoEntradaDTO dto) {
        Produto produtoAntigo = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));

        Produto copiaProdutoAntigo = new Produto();
        copiaProdutoAntigo.setId(produtoAntigo.getId());
        copiaProdutoAntigo.setNome(produtoAntigo.getNome());
        copiaProdutoAntigo.setPreco(produtoAntigo.getPreco());
        copiaProdutoAntigo.setCategoria(produtoAntigo.getCategoria());

        produtoAntigo.setNome(dto.getNome());
        produtoAntigo.setPreco(dto.getPreco());
        produtoAntigo.setCategoria(dto.getCategoria());

        observador.forEach(obs -> obs.notificacaoProdutoAlterado(copiaProdutoAntigo, produtoAntigo));

        produtoRepository.save(produtoAntigo);
        return ResponseEntity.ok("Produto atualizado com sucesso.");
    }

    public ResponseEntity<String> removerProduto(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Produto não encontrado.");
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.ok("Produto removido com sucesso.");
    }

    private ProdutoSaidaDTO converterParaSaidaDTO(Produto produto) {
        List<Integer> notificacoesIds = Optional.ofNullable(produto.getNotificacoes())
                .orElse(Collections.emptyList())
                .stream()
                .map(Notificacao::getId)
                .collect(Collectors.toList());

        ProdutoSaidaDTO dto = new ProdutoSaidaDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setPreco((double) produto.getPreco());
        dto.setCategoria(produto.getCategoria());
        dto.setIdNotificacao(notificacoesIds);
        return dto;
    }

    // CONVERTER DTO DE ENTRADA PARA ENTIDADE
    private Produto converterParaEntidade(ProdutoEntradaDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        return produto;
    }
}
