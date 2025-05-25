package com.back.SteelTech.Controller;

import com.back.SteelTech.DTO.ProdutoEntradaDTO;
import com.back.SteelTech.DTO.ProdutoSaidaDTO;
import com.back.SteelTech.Service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoSaidaDTO>> listarTodos() {
        return produtoService.listarTodosProdutos();
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody ProdutoEntradaDTO dto) {
        return produtoService.cadastrarProduto(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Integer id, @RequestBody ProdutoEntradaDTO dto) {
        return produtoService.atualizarProduto(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable Integer id) {
        return produtoService.removerProduto(id);
    }
}
