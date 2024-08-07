package com.egscapekr.user.entity;

import com.egscapekr.user.dto.DiscussGameAliasReqDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gameAlias", "game_id"})
})
public class DiscussGameAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discussGameAliasId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="game_id", nullable=false)
    private Game game;
    private String gameAlias;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", nullable = false)
    private User user; // 토론을 생성한 유저의 username

    private int agree = 0; // 현재까지 찬성 수
    private int disagree = 0; // 현재까지 반대 수
    private LocalDateTime createAt; // 투표 생성일
    private LocalDateTime dueTo; // 투표 마감일
    @OneToMany(mappedBy = "discussGameAlias", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameAliasVote> votes;

    public DiscussGameAlias(DiscussGameAliasReqDTO discussGameAliasReqDTO){
        this.createAt = discussGameAliasReqDTO.getCreateAt();
        this.gameAlias = discussGameAliasReqDTO.getGameAlias();
    }
}
