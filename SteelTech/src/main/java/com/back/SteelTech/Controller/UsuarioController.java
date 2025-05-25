package com.back.SteelTech.Controller;

import com.back.SteelTech.DTO.UsuarioEntradaDTO;
import com.back.SteelTech.DTO.UsuarioSaidaDTO;
import com.back.SteelTech.Service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody UsuarioEntradaDTO dto) {
        return usuarioService.createUsuario(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioSaidaDTO>> listarUsuarios() {
        return usuarioService.findAllUsuarios();
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable String cpf, @RequestBody UsuarioEntradaDTO dto) {
        return usuarioService.updateUsuario(cpf, dto);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String cpf) {
        return usuarioService.deleteUsuario(cpf);
    }
}
