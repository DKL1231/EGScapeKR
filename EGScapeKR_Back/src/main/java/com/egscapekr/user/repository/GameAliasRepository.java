package com.egscapekr.user.repository;

import com.egscapekr.user.entity.GameAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameAliasRepository extends JpaRepository<GameAlias, Integer> {
    @Query("SELECT g FROM GameAlias g WHERE g.game.gameId = :gameId")
    List<GameAlias> findByGameId(@Param("gameId") int gameId);

    @Query("SELECT DISTINCT(g.game.gameId) FROM GameAlias g WHERE g.gameAliasName LIKE :gameAlias")
    List<Integer> findByGameAliasName(@Param("gameAlias") String gameAlias);
}
