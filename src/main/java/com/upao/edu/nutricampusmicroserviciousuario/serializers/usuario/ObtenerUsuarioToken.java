package com.upao.edu.nutricampusmicroserviciousuario.serializers.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObtenerUsuarioToken {
    private String token;
}
