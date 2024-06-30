package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "ejercicio_rutina")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EjercicioRutina {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ejercicio_rutina")
    private Long idEjercicioRutina;
    @Column(name = "repeticiones", nullable = false)
    private int repeticiones;
    @ManyToOne
    @JoinColumn(name = "id_rutina", nullable = false)
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio", nullable = false)
    @JsonIgnore
    private Ejercicio ejercicio;

    @ManyToOne
    @JoinColumn(name = "id_hora_dia", nullable = false)
    @JsonIgnore
    private HoraDia horaDia;

}
