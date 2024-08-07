package com.egscapekr.user.repository;

import com.egscapekr.user.entity.DiscussGameCreate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussGameCreateRepository extends JpaRepository<DiscussGameCreate, Integer> {
}
