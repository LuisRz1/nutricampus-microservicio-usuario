package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal.CronogramaSemanal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "rutina_cronograma")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutinaCronograma {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_rutina_cronograma")
    private Long idRutinaCronograma;

    @ManyToOne
    @JoinColumn(name = "id_cronograma_semanal", nullable = false)
    @JsonIgnore
    private CronogramaSemanal cronogramaSemanal;

    @ManyToOne
    @JoinColumn(name = "id_rutina", nullable = false)
    private Rutina rutina;
}
