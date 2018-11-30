package com.dev.sigrid.likemindedserver.repository;

import com.dev.sigrid.likemindedserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);
}
