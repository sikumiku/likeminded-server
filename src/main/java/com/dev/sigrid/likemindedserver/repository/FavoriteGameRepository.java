package com.dev.sigrid.likemindedserver.repository;

import com.dev.sigrid.likemindedserver.domain.FavoriteGame;
import com.dev.sigrid.likemindedserver.domain.Game;
import com.dev.sigrid.likemindedserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteGameRepository extends JpaRepository<FavoriteGame, Long> {
    @Modifying
    @Query("delete from FavoriteGame fg where fg.id = ?1")
    void delete(Long entityId);
}
