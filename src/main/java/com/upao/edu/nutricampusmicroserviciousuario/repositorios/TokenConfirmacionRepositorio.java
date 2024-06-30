package com.upao.edu.nutricampusmicroserviciousuario.repositorios;


import com.upao.edu.nutricampusmicroserviciousuario.modelos.TokenConfirmacion;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenConfirmacionRepositorio extends JpaRepository<TokenConfirmacion, Long> {
    boolean existsTokenConfirmacionByToken(String token);
    TokenConfirmacion findByToken(String token);
    TokenConfirmacion saveAndFlush(TokenConfirmacion confirmationToken);
    TokenConfirmacion findByUsuario(Usuario usuario);
}
