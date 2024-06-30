package com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrearUsuarioRequest {
    private String nombreUsuario;
    private String nombreCompleto;
    private String correo;
    private String contra;
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
}
