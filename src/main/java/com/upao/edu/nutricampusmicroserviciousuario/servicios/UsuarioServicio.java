package com.upao.edu.nutricampusmicroserviciousuario.servicios;


import com.upao.edu.nutricampusmicroserviciousuario.excepciones.RecursoExistenteExcepcion;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import com.upao.edu.nutricampusmicroserviciousuario.repositorios.UsuarioRepositorio;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.DietaCronograma;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.Rutina;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.RutinaCronograma;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal.CronogramaSemanalSerializer;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.CrearUsuarioRequest;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.EditarUsuarioRequest;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario.UsuarioSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired private UsuarioRepositorio usuarioRepositorio;
    @Autowired private RestTemplate restTemplate;
    @Value("${dieta.service.url}")
    private String url;
    @Value("${rutina.service.url}")
    private String urlRutina;

    // READ
    public List<UsuarioSerializer> listarUsuarios(){return usuarioRepositorio.findAll().stream().map(this::retornarUsuarioSerializer).toList();}

    // CREATE
    public Usuario crearUsuario(CrearUsuarioRequest request){
        Usuario usuario = new Usuario(null, request.getNombreUsuario(), request.getNombreCompleto(), request.getCorreo(), request.getContra(), request.getFoto(), request.getEdad(), request.getPeso(), request.getTalla(), request.getGenero(), request.getNivelActividad(), request.getHistorialSalud(), request.getMeta(), request.getPreferenciasDieteticas(), request.getAlimentos(), null);
        if(usuarioRepositorio.existsUsuarioByNombreUsuario(usuario.getNombreUsuario())){
            throw new RecursoExistenteExcepcion("El usuario "+usuario.getNombreUsuario()+" existe");
        }
        if(usuarioRepositorio.existsUsuarioByCorreo(usuario.getCorreo())){
            throw new RecursoExistenteExcepcion("El email ya ha sido usado para la creación de otro usuario");
        }
        return usuarioRepositorio.save(usuario);
    }

    // UPDATE
    public UsuarioSerializer editarUsuario(String nombreUsuario, EditarUsuarioRequest request){
        Usuario usuario = buscarPorNombreUsuario(nombreUsuario);
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setNombreCompleto(request.getNombreCompleto());
        usuario.setFoto(request.getFoto());
        usuario.setEdad(request.getEdad());
        usuario.setPeso(request.getPeso());
        usuario.setTalla(request.getTalla());
        usuario.setGenero(request.getGenero());
        usuario.setNivelActividad(request.getNivelActividad());
        usuario.setHistorialSalud(request.getHistorialSalud());
        usuario.setMeta(request.getMeta());
        usuario.setPreferenciasDieteticas(request.getPreferenciasDieteticas());
        usuario.setAlimentos(request.getAlimentos());
        usuario.setActivo(request.isActivo());
        actualizarCambios(usuario);
        return retornarUsuarioSerializer(usuario);
    }

    // DELETE
    public List<UsuarioSerializer> eliminarusuario(String nombreUsuario){
        Usuario usuario = buscarPorNombreUsuario(nombreUsuario);
        usuarioRepositorio.delete(usuario);
        return listarUsuarios();
    }

    // Mapear a Serializer
    public UsuarioSerializer retornarUsuarioSerializer(Usuario usuario){
        List<CronogramaSemanalSerializer> cronogramas = new ArrayList<>();
        List<Object> rutinas = new ArrayList<>();
        List<Object> dietas = new ArrayList<>();
        if(usuario.getCronogramaUsuario() != null){
            for(int i = 0; i < usuario.getCronogramaUsuario().size(); i++){
                for(RutinaCronograma rutinaEjercicio : usuario.getCronogramaUsuario().get(i).getCronogramaSemanal().getRutinaCronogramas()){
                    rutinas.add(restTemplate.postForObject(urlRutina+"/rutina/serializer/", rutinaEjercicio.getRutina(), Object.class));
                }
                for(DietaCronograma dietaCronograma : usuario.getCronogramaUsuario().get(i).getCronogramaSemanal().getDietaCronogramas()){
                    dietas.add(restTemplate.postForObject(url+"/dieta/serializer/", dietaCronograma.getDieta(), Object.class));
                }
                cronogramas.add(new CronogramaSemanalSerializer(usuario.getCronogramaUsuario().get(i).getCronogramaSemanal().getFechaInicio(), usuario.getCronogramaUsuario().get(i).getCronogramaSemanal().getFechaFin(), usuario.getCronogramaUsuario().get(i).getCronogramaSemanal().getDia(), usuario.getCronogramaUsuario().get(i).getCronogramaSemanal().isCompletado(), rutinas, dietas));
            }
        }
        return new UsuarioSerializer(usuario.getNombreUsuario(), usuario.getNombreCompleto(), usuario.getFoto(), usuario.getEdad(), usuario.getPeso(), usuario.getTalla(), usuario.getGenero(), usuario.getNivelActividad(), usuario.getHistorialSalud(), usuario.getMeta(), usuario.getPreferenciasDieteticas(), usuario.getAlimentos(), cronogramas);
    }

    // Buscar por nombre de usuario
    public Usuario buscarPorNombreUsuario(String nombreUsuario){
        Optional<Usuario> usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario);
        if(usuario.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario.get();
    }

    public void actualizarCambios(Usuario usuario) {
        usuarioRepositorio.saveAndFlush(usuario);
    }
}
