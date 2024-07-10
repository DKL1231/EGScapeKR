package com.egscapekr.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue
    private int gameId;
    private int id;
    private String gameNameOrigin; // 게임명 원본
    private String gameNameTrans; // 게임명 대표번역
    private Timestamp sellDay; // 판매개시일
    private int brandId; // 브랜드명
    private int median; // 점수, 하루한번 계산
    private int count; // 획득표수, 하루한번 계산
    private int eroscapeMedian; // 에로스케 점수
    private int eroscapeCount; // 에로스케 획득표수
    private Timestamp eroscapeUpdateDay; // 에로스케 점수 업데이트일
    private int getchu; // 겟츄 아이디
    private String url; // 홈페이지 링크
    private String model; // 플랫폼(Android, PC, PSP등)
    private Boolean erogame; // R-18 여부
    private String genre; // 장르
    private String twitter; // 트위터 아이디
    private String vndb; // vndb아이디
}
