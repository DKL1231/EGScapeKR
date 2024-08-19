package com.egscapekr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussBrandAliasReqDTO {
    private String username;
    private int brandId;
    private String brandAlias; // 추가하고자 하는 별명
    private LocalDateTime createAt;
}
