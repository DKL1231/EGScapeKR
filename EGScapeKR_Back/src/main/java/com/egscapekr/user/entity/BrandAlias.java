package com.egscapekr.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BrandAlias {
    @Id
    @GeneratedValue
    private int brandAliasId;
    private int brandId;
    private String alias;
}
