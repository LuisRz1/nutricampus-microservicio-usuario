package com.upao.edu.nutricampusmicroserviciousuario.servicios;


import com.upao.edu.nutricampusmicroserviciousuario.excepciones.RecursoNoExistente;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.TokenConfirmacion;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import com.upao.edu.nutricampusmicroserviciousuario.repositorios.TokenConfirmacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServicio {

    @Autowired private TokenConfirmacionRepositorio tokenConfirmacionRepositorio;
    @Autowired private EmailServicio emailServicio;

    public void enviarEmail(Usuario usuario) { //Cambiar URL a la del back desplegado
        String token = generarToken(usuario);
        String url = "https://nutricampus-microservicio-usuario.onrender.com/autenticacion/token-confirmacion/"+token; //modificar puerto
        String mensaje = "Felicidades "+usuario.getNombreUsuario()+" por registrar su cuenta, estas a un solo paso de poder hacer uso " +
                "de las funciones de Nutricampus, entra a este link para que puedas registrate," +url;
        emailServicio.sendEmail(usuario.getCorreo(), "Activacion de cuenta", mensaje);
    }

    public TokenConfirmacion encontrarToken(String token){
        if(!tokenConfirmacionRepositorio.existsTokenConfirmacionByToken(token)){
            throw new RecursoNoExistente("Token no válido");
        }
        return tokenConfirmacionRepositorio.findByToken(token);
    }

    public void guardarCambios(TokenConfirmacion tokenConfirmacion){tokenConfirmacionRepositorio.saveAndFlush(tokenConfirmacion);}

    private String generarToken(Usuario usuario){
        String token = UUID.randomUUID().toString();
        TokenConfirmacion tokenConfirmacion = new TokenConfirmacion(null, token, usuario);
        return tokenConfirmacionRepositorio.save(tokenConfirmacion).getToken();
    }

    public void eliminarToken(Usuario usuario){
        tokenConfirmacionRepositorio.delete(tokenConfirmacionRepositorio.findByUsuario(usuario));
    }
}