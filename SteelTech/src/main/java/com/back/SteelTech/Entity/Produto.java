package com.back.SteelTech.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private Double preco;
    private String categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoes;
}
