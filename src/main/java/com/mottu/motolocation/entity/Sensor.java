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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensor_seq")
    @SequenceGenerator(name = "sensor_seq", sequenceName = "SENSOR_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String codigo;

    private int posicaoX;
    private int posicaoY;
    private String descricao;
}