package com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class CronogramaSemanalSerializer {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String dia;
    private boolean completado;
    private List<Object> rutinas;
    private List<Object> dietas;
}
