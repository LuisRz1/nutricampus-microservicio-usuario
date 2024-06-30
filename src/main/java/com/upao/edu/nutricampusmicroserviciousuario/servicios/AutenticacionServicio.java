package com.upao.edu.nutricampusmicroserviciousuario.servicios;


import com.upao.edu.nutricampusmicroserviciousuario.excepciones.EmailUsadoExcepcion;
import com.upao.edu.nutricampusmicroserviciousuario.excepciones.TokenExpiradoExcepcion;
import com.upao.edu.nutricampusmicroserviciousuario.excepciones.UsuarioNoActivadoExcepcion;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.TokenConfirmacion;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.*;
import com.upao.edu.nutricampusmicroserviciousuario.util.EncryptionUtil;
import com.upao.edu.nutricampusmicroserviciousuario.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class AutenticacionServicio {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UsuarioServicio usuarioServicio;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private TokenServicio tokenServicio;

    public UsuarioSerializer registrarUsuario(CrearUsuarioRequest request) {
        request.setContra(passwordEncoder.encode(request.getContra()));
        Usuario usuario = usuarioServicio.crearUsuario(request);
        tokenServicio.enviarEmail(usuario);
        return usuarioServicio.retornarUsuarioSerializer(usuario);
    }

    public String ConfirmarCuenta(String token){
        TokenConfirmacion tokenConfirmacion = tokenServicio.encontrarToken(token);
        if(tokenConfirmacion.getFechaActivacion() != null){
            throw new EmailUsadoExcepcion("Este email ya ha sido confirmado");
        }
        LocalDateTime fechaExpiracion = tokenConfirmacion.getFechaExpiracion();
        if(fechaExpiracion.isBefore(LocalDateTime.now())){
            throw new TokenExpiradoExcepcion("Token expirado");
        }
        tokenConfirmacion.setFechaActivacion(LocalDateTime.now());
        tokenServicio.guardarCambios(tokenConfirmacion);
        Usuario usuario = tokenConfirmacion.getUsuario();
        EditarUsuarioRequest request = new EditarUsuarioRequest(usuario.getNombreUsuario(), usuario.getNombreCompleto(), usuario.getFoto(), usuario.getEdad(), usuario.getPeso(), usuario.getTalla(), usuario.getGenero(), usuario.getNivelActividad(), usuario.getHistorialSalud(), usuario.getMeta(), usuario.getPreferenciasDieteticas(), usuario.getAlimentos(),true);
        usuarioServicio.editarUsuario(usuario.getNombreUsuario(), request);
        return " <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"100%\">\n" +
                "    <tr>\n" +
                "      <td style=\"text-align: center; padding: 50px 0;\">\n" +
                "        <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"600\"\n" +
                "          style=\"background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);\">\n" +
                "          <tr>\n" +
                "            <td style=\"padding: 40px;\">\n" +
                "              <h6 style=\"text-align: center; font-size: 18px; color: #000; text-transform: uppercase; font-weight: bold; font-family: 'Roboto Mono', monospace;\">\n" +
                "                NUTRICAMPUS</h6>\n" +
                "              <h3 style=\"text-align: center; font-size: 24px; color: #007bff; font-weight: bold; font-family: 'Roboto', sans-serif;\">\n" +
                "                ACTIVACIÓN DE CUENTA</h3>\n" +
                "              <p style=\"text-align: center; font-size: 18px; color: #000;\">Se activó la cuenta correctamente.</p>\n" +
                "              <div style=\"text-align: center; margin-top: 30px;\">\n" +
                "                <a href=\"#\"\n" + //Poner link del login del front en donde esta el "#"
                "                  style=\"display: inline-block; padding: 12px 24px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 5px; font-size: 18px; font-weight: bold;\">\n" +
                "                  Iniciar sesión\n" +
                "                </a>\n" +
                "              </div>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </table>";
    }

    public AutenticacionUsuarioResponse autenticarUsuario(AutenticacionUsuarioRequest request) throws Exception {
        Usuario usuario = usuarioServicio.buscarPorNombreUsuario(request.getNombreUsuario());
        if (usuario.isActivo()) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNombreUsuario(), request.getContra()));
                return new AutenticacionUsuarioResponse(EncryptionUtil.encrypt(jwtTokenUtil.generateToken(usuario)));
            } catch (AuthenticationException e){}
        } else {
            throw new UsuarioNoActivadoExcepcion("Cuenta no activada");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario y/o password incorrectos");
    }

    public TokenResponse obtenerNombreUsuario(String request){
        String token = EncryptionUtil.decrypt(request);
        return new TokenResponse(jwtTokenUtil.getUserNameFromToken(token));
    }
}