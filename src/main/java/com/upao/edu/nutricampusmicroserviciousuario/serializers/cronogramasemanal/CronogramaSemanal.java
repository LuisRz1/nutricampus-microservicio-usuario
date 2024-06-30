package com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.CronogramaUsuario;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.DietaCronograma;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.RutinaCronograma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cronograma_semanal")
@Entity
public class CronogramaSemanal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cronograma_semanal")
    private Long idCronogramaSemanal;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;
    @Column(name = "dia", nullable = false)
    private String dia;
    @Column(name = "completado", nullable = false)
    private boolean completado;
    @OneToMany(mappedBy = "cronogramaSemanal", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CronogramaUsuario> cronogramaUsuario;
    @OneToMany(mappedBy = "cronogramaSemanal", cascade = CascadeType.ALL)
    private List<RutinaCronograma> rutinaCronogramas;
    @OneToMany(mappedBy = "cronogramaSemanal", cascade = CascadeType.ALL)
    private List<DietaCronograma> dietaCronogramas;
}
