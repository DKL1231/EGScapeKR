package com.egscapekr.user.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameDetailDTO {
    private String gameNameOrigin;
    private List<String> gameAliasList;
    private Timestamp sellDay;
    private Integer median;
    private Integer count;
    private Integer eroscapeMedian;
    private Integer eroscapeCount;
    private Timestamp eroscapeUpdateDay;
    private Integer getchu;
    private String url; // 홈페이지 링크
    private String model; // 플랫폼(Android, PC, PSP등)
    private Boolean erogame; // R-18 여부
    private String genre; // 장르
    private String twitter; // 트위터 아이디
    private String vndb; // vndb아이디
    private String brandName;
    private Integer brandId;
}
