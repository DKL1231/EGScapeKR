package com.egscapekr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscussBrandCreateReqDTO {
    private String username;
    private LocalDateTime createAt;

    private int brandId;
    private String brandName;
    private String url;
    private String twitter;
}
