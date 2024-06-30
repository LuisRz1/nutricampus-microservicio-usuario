package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.upao.edu.nutricampusmicroserviciousuario.modelos.Usuario;
import com.upao.edu.nutricampusmicroserviciousuario.serializers.cronogramasemanal.CronogramaSemanal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cronograma_usuario")
@Entity
public class CronogramaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cronograma_usuario")
    private Long idCronogramaUsuario;
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_cronograma_semanal", nullable = false)
    private CronogramaSemanal cronogramaSemanal;

}