package com.upao.edu.nutricampusmicroserviciousuario.controladores;


import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.EditarUsuarioRequest;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.UsuarioSerializer;
import com.upao.edu.nutricampusmicroserviciousuario.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioControlador {

    @Autowired private UsuarioServicio usuarioServicio;

    @GetMapping("/listar/")
    public List<UsuarioSerializer> listarUsuarios(){
        return usuarioServicio.listarUsuarios();
    }

    @PutMapping("/editar/{usuario}")
    public UsuarioSerializer editarUsuario(@PathVariable(name = "usuario") String usuario, @RequestBody EditarUsuarioRequest request){
        return usuarioServicio.editarUsuario(usuario, request);
    }

    @DeleteMapping("/eliminar/{usuario}")
    public List<UsuarioSerializer> eliminarUsuario(@PathVariable(name = "usuario") String usuario){
        return usuarioServicio.eliminarusuario(usuario);
    }

    @GetMapping("/buscar-por-usuario/{usuario}")
    public UsuarioSerializer buscarPorUsuario(@PathVariable(name = "usuario") String request){
        return usuarioServicio.retornarUsuarioSerializer(usuarioServicio.buscarPorNombreUsuario(request));
    }

    @PostMapping("/actualizar-cambios/")
    public void actualizarCambios(@RequestBody Usuario usuario){
        usuarioServicio.actualizarCambios(usuario);
    }
}
