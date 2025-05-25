package com.back.SteelTech.DTO;

import lombok.Data;

@Data
public class NotificacaoDTO {
    private int id;
    private String mensagem;
    private String categoria;
    private String status;
    private String cpfUsuario;
    private int idProduto;
}
