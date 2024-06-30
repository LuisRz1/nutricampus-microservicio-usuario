package com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario;

import com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal.CronogramaSemanalSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UsuarioSerializer {
    private String nombreUsuario;
    private String nombreCompleto;
    private String foto;
    private int edad;
    private double peso;
    private double talla;
    private char genero;
    private char nivelActividad;
    private String historialSalud;
    private char meta;
    private char preferenciasDieteticas;
    private String alimentos;
    private List<CronogramaSemanalSerializer> cronogramaSemanal;
}
