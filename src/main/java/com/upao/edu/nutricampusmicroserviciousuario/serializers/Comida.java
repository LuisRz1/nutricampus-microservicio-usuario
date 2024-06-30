package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comida")
@Entity
public class Comida {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_comida")
    private Long idComida;
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    @Column(name = "tipo", nullable = false)
    private String tipo;
    @OneToMany(mappedBy = "comida", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DietaComida> dietaComidas;
}
