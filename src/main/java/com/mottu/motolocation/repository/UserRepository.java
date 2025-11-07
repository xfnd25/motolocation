package com.mottu.motolocation.repository;

import com.mottu.motolocation.entity.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
