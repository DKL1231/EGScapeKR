package com.egscapekr.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value="refreshToken", timeToLive=60*60*24*5) // 5일간 유지
public class RefreshToken {

    @Id
    private String username;
    private String role;
    private String refreshToken;

    @Indexed
    private String accessToken;
}
