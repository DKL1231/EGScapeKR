package com.egscapekr.user.entity;

import com.egscapekr.user.dto.DiscussBrandAliasReqDTO;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gameAlias", "game_id"})
})
public class DiscussBrandAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discussBrandAliasId;

    @ManyToOne
    @JoinColumn(name="brand_id", nullable = false)
    private Brand brand;
    private String brandAlias;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user; // 토론을 생성한 유저의 username

    private int agree = 0; // 현재까지 찬성 수
    private int disagree = 0; // 현재까지 반대 수
    private LocalDateTime createAt; // 투표 생성일
    private LocalDateTime dueTo; // 투표 마감일


    public DiscussBrandAlias(DiscussBrandAliasReqDTO discussBrandAliasReqDTO) {
        this.createAt = discussBrandAliasReqDTO.getCreateAt();
        this.brandAlias = discussBrandAliasReqDTO.getBrandAlias();
    }
}
