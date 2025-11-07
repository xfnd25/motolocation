package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.Moto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MotoRepositoryImpl implements MotoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Moto create(Moto moto) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_create_moto");

        query.registerStoredProcedureParameter("p_ano", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_modelo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_placa", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rfid_tag", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_ano", moto.getAno());
        query.setParameter("p_modelo", moto.getModelo());
        query.setParameter("p_placa", moto.getPlaca());
        query.setParameter("p_rfid_tag", moto.getRfidTag());

        query.execute();

        Long id = (Long) query.getOutputParameterValue("p_id");
        moto.setId(id);
        return moto;
    }

    @Override
    public Moto update(Moto moto) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_update_moto");

        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_ano", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_modelo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_placa", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_rfid_tag", String.class, ParameterMode.IN);

        query.setParameter("p_id", moto.getId());
        query.setParameter("p_ano", moto.getAno());
        query.setParameter("p_modelo", moto.getModelo());
        query.setParameter("p_placa", moto.getPlaca());
        query.setParameter("p_rfid_tag", moto.getRfidTag());

        query.execute();
        return moto;
    }

    @Override
    public void delete(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_delete_moto");
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.setParameter("p_id", id);
        query.execute();
    }

    @Override
    public Optional<Moto> findById(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_get_moto_by_id", Moto.class);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_id", id);

        List<Moto> result = query.getResultList();
        return result.stream().findFirst();
    }

    @Override
    public List<Moto> findAll() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_list_motos", Moto.class);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        return query.getResultList();
    }

    @Override
    public Optional<Moto> findByPlaca(String placa) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_get_moto_by_placa", Moto.class);
        query.registerStoredProcedureParameter("p_placa", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_placa", placa);

        List<Moto> result = query.getResultList();
        return result.stream().findFirst();
    }

    @Override
    public Optional<Moto> findByRfidTag(String rfidTag) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_get_moto_by_rfid", Moto.class);
        query.registerStoredProcedureParameter("p_rfid_tag", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_rfid_tag", rfidTag);

        List<Moto> result = query.getResultList();
        return result.stream().findFirst();
    }

    @Override
    public Page<Moto> findAllPaginated(int page, int size, String filter) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_MOTO.sp_list_motos_paginada", Moto.class);

        query.registerStoredProcedureParameter("p_page_number", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_size", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_filter", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("p_total_records", Integer.class, ParameterMode.OUT);

        query.setParameter("p_page_number", page);
        query.setParameter("p_page_size", size);
        query.setParameter("p_filter", filter);

        List<Moto> motos = query.getResultList();
        Integer totalRecords = (Integer) query.getOutputParameterValue("p_total_records");

        return new PageImpl<>(motos, PageRequest.of(page, size), totalRecords);
    }
}
