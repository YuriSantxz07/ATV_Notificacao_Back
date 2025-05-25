package com.back.SteelTech.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "cpf_usuario")
    private Usuario usuario;

    private String mensagem;
    private String categoria;
    private String status;
}
