package com.back.SteelTech.Service;

import com.back.SteelTech.DTO.UsuarioEntradaDTO;
import com.back.SteelTech.DTO.UsuarioSaidaDTO;
import com.back.SteelTech.Entity.Usuario;
import com.back.SteelTech.Repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<String> createUsuario(UsuarioEntradaDTO dto) {
        validarEmailECpf(dto);

        if (usuarioRepository.existsById(dto.getCpf())) {
            throw new ResponseStatusException(BAD_REQUEST, "O CPF já está cadastrado.");
        }

        usuarioRepository.save(toEntity(dto));
        return ResponseEntity.ok("Usuário cadastrado com sucesso..");
    }

    public ResponseEntity<List<UsuarioSaidaDTO>> findAllUsuarios() {
        List<UsuarioSaidaDTO> usuarios = usuarioRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    public ResponseEntity<String> updateUsuario(String cpf, UsuarioEntradaDTO dto) {
        validarEmailECpf(dto);

        Usuario usuario = usuarioRepository.findById(cpf)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado."));

        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }

    public ResponseEntity<String> deleteUsuario(String cpf) {
        if (!usuarioRepository.existsById(cpf)) {
            throw new ResponseStatusException(NOT_FOUND, "Usuário não encontrado.");
        }

        usuarioRepository.deleteById(cpf);
        return ResponseEntity.ok("Usuário deletado com sucesso.");
    }

    private UsuarioSaidaDTO toDto(Usuario usuario) {
        List<Integer> notificacoesIds = Optional.ofNullable(usuario.getNotificacoes())
                .orElse(Collections.emptyList())
                .stream()
                .map(notificacao -> notificacao.getId().intValue())
                .collect(Collectors.toList());

        UsuarioSaidaDTO dto = new UsuarioSaidaDTO();
        dto.setCpf(usuario.getCpf());
        dto.setNome(usuario.getNome());
        dto.setSobrenome(usuario.getSobrenome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        dto.setIdNotificacao(notificacoesIds);

        return dto;
    }

    private Usuario toEntity(UsuarioEntradaDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.getCpf());
        usuario.setNome(dto.getNome());
        usuario.setSobrenome(dto.getSobrenome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuario;
    }

    private void validarEmailECpf(UsuarioEntradaDTO usuario) {
        if (usuario.getCpf() == null || usuario.getCpf().length() != 11) {
            throw new IllegalArgumentException("O CPF deve ter 11 dígitos.");
        }

        if (!usuario.getEmail().matches("\\S+@\\S+\\.\\S+")) {
            throw new IllegalArgumentException("Email inválido.");
        }
    }
}

