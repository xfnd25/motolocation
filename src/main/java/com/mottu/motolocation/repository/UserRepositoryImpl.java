package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_USER.sp_get_user_by_username", User.class);
        query.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", Class.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_username", username);

        List<User> result = query.getResultList();
        return result.stream().findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            return create(user);
        } else {
            return update(user);
        }
    }

    private User create(User user) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_USER.sp_create_user");

        query.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_role", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.OUT);

        query.setParameter("p_username", user.getUsername());
        query.setParameter("p_password", user.getPassword());
        query.setParameter("p_role", user.getRole().toString());

        query.execute();

        Long id = (Long) query.getOutputParameterValue("p_id");
        user.setId(id);
        return user;
    }

    private User update(User user) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("PKG_USER.sp_update_user");

        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_role", String.class, ParameterMode.IN);

        query.setParameter("p_id", user.getId());
        query.setParameter("p_username", user.getUsername());
        query.setParameter("p_password", user.getPassword());
        query.setParameter("p_role", user.getRole().toString());

        query.execute();
        return user;
    }
}
