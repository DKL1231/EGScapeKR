package com.egscapekr.user.dto;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameScoreDTO {
    private int gameId;
    private Integer score;
    private Timestamp voteDate;
    private String title;
    private String content;
    private Boolean spoiler;
}
