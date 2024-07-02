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
    public UsuarioSerializer editarUsuario(@PathVariable() String usuario, @RequestBody EditarUsuarioRequest request){
        return usuarioServicio.editarUsuario(usuario, request);
    }

    @DeleteMapping("/eliminar/{usuario}")
    public List<UsuarioSerializer> eliminarUsuario(@PathVariable() String usuario){
        return usuarioServicio.eliminarusuario(usuario);
    }

    @GetMapping("/buscar-por-usuario/{usuario}")
    public UsuarioSerializer buscarPorUsuario(@PathVariable() String usuario){
        return usuarioServicio.retornarUsuarioSerializer(usuarioServicio.buscarPorNombreUsuario(usuario));
    }

    @PostMapping("/actualizar-cambios/")
    public void actualizarCambios(@RequestBody Usuario usuario){
        usuarioServicio.actualizarCambios(usuario);
    }
}
