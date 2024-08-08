package com.egscapekr.user.entity;

import com.egscapekr.user.dto.DiscussGameCreateReqDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscussGameCreate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discussGameCreateId;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user; // 토론을 생성한 유저의 username

    private int agree = 0; // 현재까지 찬성 수
    private int disagree = 0; // 현재까지 반대 수
    private LocalDateTime createAt; // 투표 생성일
    private LocalDateTime dueTo; // 투표 마감일


    private int gameId; // 에로스케 게임Id

    private String gameNameOrigin; // 게임명 원본
    private Timestamp sellDay; // 판매개시일
    private Integer getchu; // 겟츄 아이디
    private String url; // 홈페이지 링크
    private String model; // 플랫폼(Android, PC, PSP등)
    private Boolean erogame; // R-18 여부
    private String genre; // 장르
    private String twitter; // 트위터 아이디
    private String vndb; // vndb아이디

    @OneToMany(mappedBy = "discussGameCreate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameCreateVote> votes;

    public DiscussGameCreate(DiscussGameCreateReqDTO discussGameCreateReqDTO){
        this.createAt = discussGameCreateReqDTO.getCreateAt();
        this.gameId = discussGameCreateReqDTO.getGameId();
        this.gameNameOrigin = discussGameCreateReqDTO.getGameNameOrigin();
        this.sellDay = discussGameCreateReqDTO.getSellDay();
        this.getchu = discussGameCreateReqDTO.getGetchu();
        this.url = discussGameCreateReqDTO.getUrl();
        this.model = discussGameCreateReqDTO.getModel();
        this.erogame = discussGameCreateReqDTO.getErogame();
        this.genre = discussGameCreateReqDTO.getGenre();
        this.twitter = discussGameCreateReqDTO.getTwitter();
        this.vndb = discussGameCreateReqDTO.getVndb();
    }
}
