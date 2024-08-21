package com.egscapekr.user.repository;

import com.egscapekr.user.entity.DiscussBrandCreate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussBrandCreateRepository extends JpaRepository<DiscussBrandCreate, Integer> {
}
