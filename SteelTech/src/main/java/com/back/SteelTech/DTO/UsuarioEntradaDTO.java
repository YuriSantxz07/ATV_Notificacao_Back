package com.back.SteelTech.DTO;

import lombok.Data;

@Data
public class UsuarioEntradaDTO {
    private String cpf;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;

}
