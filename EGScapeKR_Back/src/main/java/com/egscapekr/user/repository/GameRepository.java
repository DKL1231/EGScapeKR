package com.egscapekr.user.repository;

import com.egscapekr.user.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g JOIN FETCH g.brand WHERE g.id IN :ids")
    List<Game> findGamesByIdsWithBrand(@Param("ids") List<Integer> ids);
}
