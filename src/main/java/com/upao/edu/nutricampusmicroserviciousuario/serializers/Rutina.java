package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rutina")
@Entity
public class Rutina {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_rutina")
    private Long idRutina;
    @Column(name = "tiempo", nullable = false)
    private LocalTime tiempo;
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EjercicioRutina> ejercicioRutinas;
    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RutinaCronograma> rutinaCronogramas;

}
