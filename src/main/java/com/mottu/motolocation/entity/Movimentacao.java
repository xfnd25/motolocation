package com.mottu.motolocation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimentacao_seq")
    @SequenceGenerator(name = "movimentacao_seq", sequenceName = "MOVIMENTACAO_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    private Moto moto;

    @ManyToOne(optional = false)
    private Sensor sensor;

    private LocalDateTime dataHora;

    @PrePersist
    public void setTimestamp() {
        this.dataHora = LocalDateTime.now();
    }
}