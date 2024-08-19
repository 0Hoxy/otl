package com.otl.user.repository;

import com.otl.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findUserByEmailAndProvider(String email, String provider);
}
