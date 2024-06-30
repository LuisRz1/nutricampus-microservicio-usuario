package com.upao.edu.nutricampusmicroserviciousuario.repositorios;


import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String usuario);

    boolean existsUsuarioByNombreUsuario(String nombreUsuario);

    boolean existsUsuarioByCorreo(String correo);
}
