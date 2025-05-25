package com.back.SteelTech.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProdutoSaidaDTO {
    private int id;
    private String nome;
    private Double preco;
    private String categoria;

    private List<Integer> idNotificacao;

}
