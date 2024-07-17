package com.egscapekr.user.repository;

import com.egscapekr.user.entity.GameAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameAliasRepository extends JpaRepository<GameAlias, Integer> {
    @Query("select g from GameAlias g where g.GameId = :gameId")
    List<GameAlias> findByGameId(@Param("gameId") int gameId);

    @Query("select distinct(g.GameId) from GameAlias g where g.GameAliasName LIKE :gameAlias")
    List<Integer> findByGameAliasName(@Param("gameAlias") String gameAlias);
}
