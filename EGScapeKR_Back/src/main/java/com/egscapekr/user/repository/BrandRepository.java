package com.egscapekr.user.repository;

import com.egscapekr.user.entity.Brand;
import com.egscapekr.user.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand b WHERE b.id IN :ids")
    List<Brand> findBrandsByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT g FROM Game g WHERE g.brand.brandId = :brandId")
    List<Game> findGamesByBrandId(@Param("brandId") int brandId);

    Brand findBrandByBrandId(int brandId);
}
