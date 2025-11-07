package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.Moto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MotoRepository {
    Moto create(Moto moto);
    Moto update(Moto moto);
    void delete(Long id);
    Optional<Moto> findById(Long id);
    List<Moto> findAll();
    Optional<Moto> findByPlaca(String placa);
    Optional<Moto> findByRfidTag(String rfidTag);
    Page<Moto> findAllPaginated(int page, int size, String filter);
}
