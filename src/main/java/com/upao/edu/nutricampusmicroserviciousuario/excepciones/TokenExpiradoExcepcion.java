package com.upao.edu.nutricampusmicroserviciousuario.excepciones;

public class TokenExpiradoExcepcion extends RuntimeException{
    public TokenExpiradoExcepcion(String mensaje){super(mensaje);}
}
