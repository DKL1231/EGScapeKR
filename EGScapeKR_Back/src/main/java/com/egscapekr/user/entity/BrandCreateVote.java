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
        @UniqueConstraint(columnNames = {"discussBrandCreateId", "username"})
})
public class BrandCreateVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="discussBrandCreateId", nullable = false)
    private DiscussBrandCreate discussBrandCreate;

    @ManyToOne
    @JoinColumn(name="username", nullable = false)
    private User user;

    private boolean isAgree;
}
