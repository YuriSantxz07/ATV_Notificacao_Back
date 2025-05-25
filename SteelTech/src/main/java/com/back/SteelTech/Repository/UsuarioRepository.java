package com.back.SteelTech.Repository;

import com.back.SteelTech.Entity.Notificacao;
import com.back.SteelTech.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
