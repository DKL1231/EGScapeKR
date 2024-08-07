package com.egscapekr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussGameCreateReqDTO {
    private String username;
    private LocalDateTime createAt;

    private int gameId; // 에로스케 게임Id

    private String gameNameOrigin; // 게임명 원본
    private Timestamp sellDay; // 판매개시일
    private Integer getchu; // 겟츄 아이디
    private String url; // 홈페이지 링크
    private String model; // 플랫폼(Android, PC, PSP등)
    private Boolean erogame; // R-18 여부
    private String genre; // 장르
    private String twitter; // 트위터 아이디
    private String vndb; // vndb아이디
}
