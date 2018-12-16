package com.dev.sigrid.likemindedserver.repository;

import com.dev.sigrid.likemindedserver.domain.Category;
import com.dev.sigrid.likemindedserver.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

