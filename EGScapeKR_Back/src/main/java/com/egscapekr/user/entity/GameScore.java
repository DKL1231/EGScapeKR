package com.egscapekr.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "game_id"})
})
public class GameScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    private Timestamp voteDate; // 투표한 날짜
    private String title; // 투표글의 제목
    private String content; // 투표글의 내용
    private Boolean spoiler; // 스포일러 여부
    private Integer score; // 투표점수
}
