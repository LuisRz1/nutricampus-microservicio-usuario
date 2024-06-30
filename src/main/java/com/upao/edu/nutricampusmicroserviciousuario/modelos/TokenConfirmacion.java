package com.upao.edu.nutricampusmicroserviciousuario.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "token_confirmacion", uniqueConstraints = {@UniqueConstraint(columnNames = {"token"})})
@Entity
public class TokenConfirmacion {
    @Id //Identifica a la primary key
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id_token") //Para que ubique a que columna agregar el valor
    private Long idToken;
    @Column(name="token")
    private String token;
    @Column(name="fecha_expiracion")
    private final LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(10);
    @Column(name="fecha_activacion")
    private LocalDateTime fechaActivacion;

    @JoinColumns({
            @JoinColumn(name="id_usuario", referencedColumnName = "id_usuario")
    })

    @OneToOne
    private Usuario usuario;

    public TokenConfirmacion(Long idToken, String token, Usuario usuario){
        this.idToken = idToken;
        this.token = token;
        this.usuario = usuario;
    }

}
