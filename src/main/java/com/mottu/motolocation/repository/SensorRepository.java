package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.Sensor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SensorRepository {
    Sensor save(Sensor sensor);
    void deleteById(Long id);
    Optional<Sensor> findById(Long id);
    List<Sensor> findAll();
    Page<Sensor> findAll(int page, int size);
    Optional<Sensor> findByCodigo(String codigo);
}
