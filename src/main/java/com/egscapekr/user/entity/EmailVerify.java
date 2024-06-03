package com.egscapekr.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value="emailVerify", timeToLive=60*60) // 1시간 유지
public class EmailVerify {

    @Id
    private String username;
    private String email;

    @Indexed
    private String verifyCode;
}
