package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.Movimentacao;
import com.mottu.motolocation.entity.Moto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class MovimentacaoRepositoryImpl implements MovimentacaoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Movimentacao save(Movimentacao movimentacao) {
        if (movimentacao.getId() == null) {
            return create(movimentacao);
        } else {
            return update(movimentacao);
        }
    }

    private Movimentacao create(Movimentacao movimentacao) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOVIMENTACAO.sp_create_movimentacao");

        query.registerStoredProcedureParameter("p_moto_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_sensor_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_timestamp_mov", java.sql.Timestamp.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_tipo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_moto_id", movimentacao.getMoto().getId());
        query.setParameter("p_sensor_id", movimentacao.getSensor().getId());
        query.setParameter("p_timestamp_mov", Timestamp.valueOf(movimentacao.getTimestampMov()));
        query.setParameter("p_tipo", movimentacao.getTipo().toString());

        query.execute();

        Long id = (Long) query.getOutputParameterValue("p_id");
        movimentacao.setId(id);
        return movimentacao;
    }

    private Movimentacao update(Movimentacao movimentacao) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOVIMENTACAO.sp_update_movimentacao");

        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_moto_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_sensor_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_timestamp_mov", java.sql.Timestamp.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_tipo", String.class, ParameterMode.IN);

        query.setParameter("p_id", movimentacao.getId());
        query.setParameter("p_moto_id", movimentacao.getMoto().getId());
        query.setParameter("p_sensor_id", movimentacao.getSensor().getId());
        query.setParameter("p_timestamp_mov", Timestamp.valueOf(movimentacao.getTimestampMov()));
        query.setParameter("p_tipo", movimentacao.getTipo().toString());

        query.execute();
        return movimentacao;
    }

    @Override
    public void deleteById(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOVIMENTACAO.sp_delete_movimentacao");
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.setParameter("p_id", id);
        query.execute();
    }

    @Override
    public Optional<Movimentacao> findById(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOVIMENTACAO.sp_get_movimentacao_by_id", Movimentacao.class);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_id", id);

        List<Movimentacao> result = query.getResultList();
        return result.stream().findFirst();
    }

    @Override
    public List<Movimentacao> findAll() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOVIMENTACAO.sp_list_movimentacoes", Movimentacao.class);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        return query.getResultList();
    }

    @Override
    public Page<Movimentacao> findByMoto(Moto moto, Pageable pageable) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOVIMENTACAO.sp_list_movimentacoes_paginada", Movimentacao.class);

        query.registerStoredProcedureParameter("p_page_number", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_size", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_moto_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("p_total_records", Integer.class, ParameterMode.OUT);

        query.setParameter("p_page_number", pageable.getPageNumber());
        query.setParameter("p_page_size", pageable.getPageSize());
        query.setParameter("p_moto_id", moto.getId());

        List<Movimentacao> movimentacoes = query.getResultList();
        Integer totalRecords = (Integer) query.getOutputParameterValue("p_total_records");

        return new PageImpl<>(movimentacoes, pageable, totalRecords);
    }
}
