package com.back.SteelTech.Service;

import com.back.SteelTech.DTO.NotificacaoDTO;
import com.back.SteelTech.Entity.Notificacao;
import com.back.SteelTech.Repository.NotificacaoRepository;
import com.back.SteelTech.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public ResponseEntity<List<NotificacaoDTO>> listarTodasNotificacoes() {
        List<NotificacaoDTO> notificacoes = notificacaoRepository.findAll().stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificacoes);
    }

    public ResponseEntity<List<NotificacaoDTO>> listarNotificacoesPorUsuario(String cpf) {
        validarExistenciaUsuario(cpf);
        List<NotificacaoDTO> notificacoes = notificacaoRepository.findByUsuario(usuarioRepository.findById(cpf).get())
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificacoes);
    }



    public ResponseEntity<String> marcarNotificacaoComoLida(Integer id) {
        Notificacao notificacao = notificacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Notificação não encontrada"));
        notificacao.setStatus("lida");
        notificacaoRepository.save(notificacao);
        return ResponseEntity.ok("Notificação marcada como lida.");
    }

    public ResponseEntity<String> excluirNotificacao(Integer id) {
        if (!notificacaoRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Notificação não encontrada");
        }
        notificacaoRepository.deleteById(id);
        return ResponseEntity.ok("Notificação deletada com sucesso.");
    }

    private NotificacaoDTO converterParaDTO(Notificacao notificacao) {
        NotificacaoDTO dto = new NotificacaoDTO();
        dto.setId(notificacao.getId());
        dto.setMensagem(notificacao.getMensagem());
        dto.setCategoria(notificacao.getCategoria());
        dto.setStatus(notificacao.getStatus());
        dto.setCpfUsuario(notificacao.getUsuario().getCpf());
        dto.setIdProduto(notificacao.getProduto().getId());
        return dto;
    }


    private void validarExistenciaUsuario(String cpf) {
        if (!usuarioRepository.existsById(cpf)) {
            throw new ResponseStatusException(NOT_FOUND, "Usuário não encontrado");
        }
    }
}
