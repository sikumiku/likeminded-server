package com.dev.sigrid.likemindedserver.repository;

import com.dev.sigrid.likemindedserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
