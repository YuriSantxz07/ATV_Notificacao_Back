package com.back.SteelTech.DTO;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioSaidaDTO {
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private List<Integer> idNotificacao;
}
