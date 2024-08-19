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
        @UniqueConstraint(columnNames = {"discussBrandAliasId", "username"})
})
public class BrandAliasVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="discussBrandAliasId", nullable = false)
    private DiscussBrandAlias discussBrandAlias;

    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    private boolean isAgree;
}
