package com.dev.sigrid.likemindedserver.repository;

import com.dev.sigrid.likemindedserver.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByName(String name);
    List<Event> findAllByUserId(Long id);

}
