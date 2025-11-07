package com.mottu.motolocation.config;

import com.mottu.motolocation.entity.Role;
import com.mottu.motolocation.entity.User;
import com.mottu.motolocation.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(Role.ROLE_ADMIN)
                        .build();
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .role(Role.ROLE_USER)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
