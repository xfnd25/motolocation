package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.Sensor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SensorRepositoryImpl implements SensorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Sensor save(Sensor sensor) {
        if (sensor.getId() == null) {
            return create(sensor);
        } else {
            return update(sensor);
        }
    }

    private Sensor create(Sensor sensor) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_create_sensor");

        query.registerStoredProcedureParameter("p_codigo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_status", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_codigo", sensor.getCodigo());
        query.setParameter("p_status", sensor.getStatus());

        query.execute();

        Long id = (Long) query.getOutputParameterValue("p_id");
        sensor.setId(id);
        return sensor;
    }

    private Sensor update(Sensor sensor) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_update_sensor");

        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_codigo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_status", String.class, ParameterMode.IN);

        query.setParameter("p_id", sensor.getId());
        query.setParameter("p_codigo", sensor.getCodigo());
        query.setParameter("p_status", sensor.getStatus());

        query.execute();
        return sensor;
    }

    @Override
    public void deleteById(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_delete_sensor");
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.setParameter("p_id", id);
        query.execute();
    }

    @Override
    public Optional<Sensor> findById(Long id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_get_sensor_by_id", Sensor.class);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_id", id);

        List<Sensor> result = query.getResultList();
        return result.stream().findFirst();
    }

    @Override
    public List<Sensor> findAll() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_list_sensors", Sensor.class);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        return query.getResultList();
    }

    @Override
    public Page<Sensor> findAll(int page, int size) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_list_sensors_paginada", Sensor.class);

        query.registerStoredProcedureParameter("p_page_number", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_page_size", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.registerStoredProcedureParameter("p_total_records", Integer.class, ParameterMode.OUT);

        query.setParameter("p_page_number", page);
        query.setParameter("p_page_size", size);

        List<Sensor> sensors = query.getResultList();
        Integer totalRecords = (Integer) query.getOutputParameterValue("p_total_records");

        return new PageImpl<>(sensors, PageRequest.of(page, size), totalRecords);
    }

    @Override
    public Optional<Sensor> findByCodigo(String codigo) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_SENSOR.sp_get_sensor_by_codigo", Sensor.class);
        query.registerStoredProcedureParameter("p_codigo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_codigo", codigo);

        List<Sensor> result = query.getResultList();
        return result.stream().findFirst();
    }
}
