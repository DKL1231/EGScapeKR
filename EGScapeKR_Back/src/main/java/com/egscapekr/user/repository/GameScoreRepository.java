package com.egscapekr.user.repository;

import com.egscapekr.user.entity.GameScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
}
