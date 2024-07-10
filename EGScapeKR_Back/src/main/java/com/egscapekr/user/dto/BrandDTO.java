package com.egscapekr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private int brandId;
    private int id;
    private String brandNameOrigin; // 브랜드명 원본
    private String brandNameTrans; // 브랜드명 대표번역
    private String url; // 브랜드링크
    private Boolean lost; // 해산 여부
    private int median; // 작품들 평균점수, 하루한번 계산
    private int eroscapeMedian; // 에로스케 기준 작품들 평균점수, 하루한번 계산
    private String twitter; // 트위터 아이디
    private int brandAliasId;
    private String alias;
}
