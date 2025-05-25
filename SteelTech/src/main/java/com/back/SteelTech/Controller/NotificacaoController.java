package com.back.SteelTech.Controller;

import com.back.SteelTech.DTO.NotificacaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final com.back.SteelTech.Service.NotificacaoService notificacaoService;

    @GetMapping
    public ResponseEntity<List<NotificacaoDTO>> listarTodas() {
        return notificacaoService.listarTodasNotificacoes();
    }

    @GetMapping("/usuario/{cpf}")
    public ResponseEntity<List<NotificacaoDTO>> listarPorUsuario(@PathVariable String cpf) {
        return notificacaoService.listarNotificacoesPorUsuario(cpf);
    }


    @PutMapping("/{id}/lida")
    public ResponseEntity<String> marcarComoLida(@PathVariable Integer id) {
        return notificacaoService.marcarNotificacaoComoLida(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNotificacao(@PathVariable Integer id) {
        return notificacaoService.excluirNotificacao(id);
    }
}
