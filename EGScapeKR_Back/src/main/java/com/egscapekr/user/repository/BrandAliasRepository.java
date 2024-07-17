package com.egscapekr.user.repository;

import com.egscapekr.user.entity.BrandAlias;
import com.egscapekr.user.entity.GameAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandAliasRepository extends JpaRepository<BrandAlias, Long> {
    @Query("select b from BrandAlias b where b.brandId = :brandId")
    List<BrandAlias> findByBrandId(@Param("brandId") int brandId);

    @Query("select distinct(b.brandId) from BrandAlias b where b.alias LIKE :brandAlias")
    List<Integer> findByBrandAliasName(@Param("brandAlias") String brandAlias);
}
