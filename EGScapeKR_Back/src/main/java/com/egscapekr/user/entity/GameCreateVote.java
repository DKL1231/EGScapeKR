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
        @UniqueConstraint(columnNames = {"discussGameCreateId", "username"})
})
public class GameCreateVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="discussGameCreateId", nullable = false)
    private DiscussGameCreate discussGameCreate;

    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    private boolean isAgree;
}
