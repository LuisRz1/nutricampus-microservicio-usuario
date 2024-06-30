package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal.CronogramaSemanal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "dieta_cronograma")
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DietaCronograma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_dieta_cronograma")
    private Long idDietaCronograma;

    @ManyToOne
    @JoinColumn(name = "id_cronograma_semanal", nullable = false)
    @JsonIgnore
    private CronogramaSemanal cronogramaSemanal;

    @ManyToOne
    @JoinColumn(name = "id_dieta", nullable = false)
    private Dieta dieta;
}
