package com.egscapekr.user.repository;

import com.egscapekr.user.entity.DiscussBrandAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussBrandAliasRepository extends JpaRepository<DiscussBrandAlias, Integer> {
    DiscussBrandAlias findByDiscussBrandAliasId(Integer discussBrandAliasId);
}
