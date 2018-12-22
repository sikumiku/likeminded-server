package com.dev.sigrid.likemindedserver.repository;

import com.dev.sigrid.likemindedserver.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByName(String name);

}
