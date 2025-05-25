package com.back.SteelTech.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
public class Usuario {
    @Id
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacao> notificacoes;

}
