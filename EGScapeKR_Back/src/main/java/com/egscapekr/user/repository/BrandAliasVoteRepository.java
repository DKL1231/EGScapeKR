package com.egscapekr.user.repository;

import com.egscapekr.user.entity.BrandAliasVote;
import com.egscapekr.user.entity.DiscussBrandAlias;
import com.egscapekr.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandAliasVoteRepository extends JpaRepository<BrandAliasVote, Long> {
    BrandAliasVote findByDiscussBrandAliasAndUser(DiscussBrandAlias discussBrandAlias, User user);
}
