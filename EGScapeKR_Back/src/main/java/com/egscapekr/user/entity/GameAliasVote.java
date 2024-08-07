package com.egscapekr.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"discussGameAliasId", "username"})
})
public class GameAliasVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="discussGameAliasId", nullable=false)
    private DiscussGameAlias discussGameAlias;

    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    private boolean isAgree;
}
