package com.egscapekr.user.repository;

import com.egscapekr.user.entity.BrandAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandAliasRepository extends JpaRepository<BrandAlias, Long> {
    @Query("SELECT b FROM BrandAlias b WHERE b.brand.brandId = :brandId")
    List<BrandAlias> findByBrandId(@Param("brandId") int brandId);

    @Query("SELECT DISTINCT(b.brand.brandId) FROM BrandAlias b WHERE b.BrandAliasName LIKE :brandAlias")
    List<Integer> findByBrandAliasName(@Param("brandAlias") String brandAlias);
}
