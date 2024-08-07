package com.egscapekr.user.repository;

import com.egscapekr.user.entity.DiscussGameAlias;
import com.egscapekr.user.entity.Game;
import com.egscapekr.user.entity.GameAliasVote;
import com.egscapekr.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameAliasVoteRepository extends JpaRepository<GameAliasVote, Long> {
    GameAliasVote findByDiscussGameAliasAndUser(DiscussGameAlias discussGameAlias, User user);
}
