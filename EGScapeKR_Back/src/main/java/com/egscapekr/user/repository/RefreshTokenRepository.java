package com.egscapekr.user.repository;

import com.egscapekr.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByAccessToken(String accessToken);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
