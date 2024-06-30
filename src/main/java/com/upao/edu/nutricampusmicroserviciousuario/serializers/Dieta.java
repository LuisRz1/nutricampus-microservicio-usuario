package com.upao.edu.nutricampusmicroserviciousuario.serializers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dieta {
    @Id
    @Column(name = "id_dieta")
    private Long idDieta;
    @JsonIgnore
    private Long idComida;
    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DietaCronograma> dietaCronogramas;
    @OneToMany(mappedBy = "dieta", cascade = CascadeType.ALL)
    private List<DietaComida> dietaComidas;
}
