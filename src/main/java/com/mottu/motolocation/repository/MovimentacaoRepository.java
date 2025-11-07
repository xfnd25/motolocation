package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.Movimentacao;
import com.mottu.motolocation.entity.Moto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository {
    Movimentacao save(Movimentacao movimentacao);
    void deleteById(Long id);
    Optional<Movimentacao> findById(Long id);
    List<Movimentacao> findAll();
    Page<Movimentacao> findByMoto(Moto moto, Pageable pageable);
}
