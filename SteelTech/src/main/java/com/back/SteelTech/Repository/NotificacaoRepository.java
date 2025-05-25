package com.back.SteelTech.Repository;

import com.back.SteelTech.Entity.Notificacao;
import com.back.SteelTech.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
    List<Notificacao> findByUsuario(Usuario usuario);
}
