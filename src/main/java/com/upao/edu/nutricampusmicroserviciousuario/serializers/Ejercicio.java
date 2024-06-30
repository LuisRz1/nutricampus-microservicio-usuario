package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ejercicio", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
@Entity
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ejercicio")
    private Long idEjercicio;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "tiempo_ejercicio", nullable = false)
    private LocalTime tiempoEjercicio;
    @Column(name = "calorias_quemadas", nullable = false)
    private double caloriasQuemadas;
    @Column(name = "velocidad_ejercicio")
    private char velocidadEjercicio;
    @OneToMany(mappedBy = "ejercicio", cascade = CascadeType.ALL)
    private List<EjercicioRutina> ejercicioRutinas;
}
