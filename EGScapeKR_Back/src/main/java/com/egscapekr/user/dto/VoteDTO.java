package com.egscapekr.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private int discussId;
    private String username;
    private boolean isAgree;
    private String type; // GC : gameCreate, GA : gameAlias, BC : brandCreate, BA : brandAlias
}
