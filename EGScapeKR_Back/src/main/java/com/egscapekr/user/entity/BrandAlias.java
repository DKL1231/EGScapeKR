package com.egscapekr.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BrandAlias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandAliasId;
    private String BrandAliasName;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
}
