package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietaComida {
    @Id
    private Long idDietaComida;
    private int raciones;
    @ManyToOne
    @JoinColumn(name = "id_dieta", nullable = false)
    @JsonIgnore
    private Dieta dieta;

    @ManyToOne
    @JoinColumn(name = "id_comida", nullable = false)
    private Comida comida;

    @ManyToOne
    @JoinColumn(name = "id_hora_dia", nullable = false)
    private HoraDia horaDia;
}
