package com.egscapekr.user.repository;

import com.egscapekr.user.entity.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandCreateVoteRepository extends CrudRepository<BrandCreateVote, Long> {
    BrandCreateVote findByDiscussBrandCreateAndUser(DiscussBrandCreate discussBrandCreate, User user);
}
