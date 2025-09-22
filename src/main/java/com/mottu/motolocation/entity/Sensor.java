package com.mottu.motolocation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String codigo;

    // Adicione a anotação @Column aqui
    @Column(name = "posicao_x")
    private int posicaoX;

    // Adicione a anotação @Column aqui
    @Column(name = "posicao_y")
    private int posicaoY;

    private String descricao;
}