package com.egscapekr.user.repository;

import com.egscapekr.user.entity.DiscussGameAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussGameAliasRepository extends JpaRepository<DiscussGameAlias, Integer> {
}
