package com.egscapekr.user.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameAliasId;

    private String gameAliasName;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;
}
