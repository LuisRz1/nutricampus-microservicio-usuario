package com.upao.edu.nutricampusmicroserviciousuario.controladores;

import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.*;
import com.upao.edu.nutricampusmicroserviciousuario.servicios.AutenticacionServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("autenticacion")

public class AutenticacionControlador {

    @Autowired private AutenticacionServicio autenticacionServicio;

    @PostMapping("/registrar/")
    public UsuarioSerializer crearUsuario(@Valid @RequestBody CrearUsuarioRequest request) {
        return autenticacionServicio.registrarUsuario(request);
    }
    @GetMapping("/token-confirmacion/{token}")
    public String activarCuenta(@PathVariable() String token){
        return autenticacionServicio.confirmarCuenta(token);
    }
    @PostMapping("/iniciar-sesion/")
    public ResponseEntity<AutenticacionUsuarioResponse> iniciarSesion(@Valid @RequestBody AutenticacionUsuarioRequest request) throws Exception {
        return ResponseEntity.ok(autenticacionServicio.autenticarUsuario(request));
    }

    @PostMapping("/obtener-usuario-token/")
    public TokenResponse obtenerUsuarioPorToken(@RequestBody ObtenerUsuarioToken request){
        return autenticacionServicio.obtenerNombreUsuario(request);
    }
}