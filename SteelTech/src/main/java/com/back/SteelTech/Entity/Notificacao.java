package com.back.SteelTech.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "cpf_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    private String mensagem;
    private String categoria;
    private String status;
}
