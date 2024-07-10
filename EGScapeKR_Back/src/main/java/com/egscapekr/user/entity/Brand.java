package com.egscapekr.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue
    private int brandId;
    private int id;
    private String brandNameOrigin; // 브랜드명 원본
    private String brandNameTrans; // 브랜드명 대표번역
    private String url; // 브랜드링크
    private Boolean lost; // 해산 여부
    private int median; // 작품들 평균점수, 하루한번 계산
    private int eroscapeMedian; // 에로스케 기준 작품들 평균점수, 하루한번 계산
    private String twitter; // 트위터 아이디
}
