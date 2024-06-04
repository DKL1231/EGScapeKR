package com.egscapekr.user.repository;

import com.egscapekr.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User findByUsername(String username);
}
