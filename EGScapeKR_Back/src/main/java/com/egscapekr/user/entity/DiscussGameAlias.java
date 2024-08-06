package com.egscapekr.user.entity;

import com.egscapekr.user.dto.DiscussGameAliasReqDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussGameAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="game_id", nullable=false)
    private Game game;
    private String gameAlias;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user; // 토론을 생성한 유저의 username

    private int agree = 1; // 현재까지 찬성 수
    private int disagree = 0; // 현재까지 반대 수
    private LocalDateTime createAt; // 투표 생성일
    private LocalDateTime dueTo; // 투표 마감일

    public DiscussGameAlias(DiscussGameAliasReqDTO discussGameAliasReqDTO){
        this.createAt = discussGameAliasReqDTO.getCreateAt();
        this.gameAlias = discussGameAliasReqDTO.getGameAlias();
    }
}
