package com.upao.edu.nutricampusmicroserviciousuario.servicios;


import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import com.upao.edu.nutricampusmicroserviciousuario.repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class JwtUserDetailsServicio implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsServicio.class);
    private final UsuarioRepositorio usuarioRepositorio;

    public JwtUserDetailsServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("---loadUserByUsername called.---");
        Optional<Usuario> user = usuarioRepositorio.findByNombreUsuario(username);
        if(user.isPresent()) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>(1);
            authorities.add(new SimpleGrantedAuthority("ADMIN"));

            return new User(user.get().getUsername(), user.get().getPassword(),true,true,true,true, authorities);
        } else {
            throw new UsernameNotFoundException("Usuario "+username+" no encontrado.");
        }
    }


}