package com.egscapekr.user.repository;

import com.egscapekr.user.entity.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameCreateVoteRepository extends CrudRepository<GameCreateVote, Long> {
    GameCreateVote findByDiscussGameCreateAndUser(DiscussGameCreate discussGameCreate, User user);
}
