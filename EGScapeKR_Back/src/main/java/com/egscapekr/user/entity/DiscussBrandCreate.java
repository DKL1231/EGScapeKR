package com.egscapekr.user.entity;

import com.egscapekr.user.dto.DiscussBrandCreateReqDTO;
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
public class DiscussBrandCreate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int discussBrandCreateId;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    private int agree = 0;
    private int disagree = 0;
    private LocalDateTime createAt;
    private LocalDateTime dueTo;

    private int brandId;
    private String brandName;
    private String url;
    private String twitter;

    @OneToMany(mappedBy = "discussBrandCreate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BrandCreateVote> votes;

    public DiscussBrandCreate(DiscussBrandCreateReqDTO discussBrandCreateReqDTO){
        this.createAt = discussBrandCreateReqDTO.getCreateAt();
        this.brandId = discussBrandCreateReqDTO.getBrandId();
        this.brandName = discussBrandCreateReqDTO.getBrandName();
        this.url = discussBrandCreateReqDTO.getUrl();
        this.twitter = discussBrandCreateReqDTO.getTwitter();
    }
}
