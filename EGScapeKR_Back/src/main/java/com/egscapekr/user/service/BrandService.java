package com.egscapekr.user.service;

import com.egscapekr.user.entity.Brand;
import com.egscapekr.user.repository.BrandAliasRepository;
import com.egscapekr.user.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringTokenizer;

@Service
public class BrandService {

    private final BrandAliasRepository brandAliasRepository;
    private final BrandRepository brandRepository;

    public BrandService(BrandAliasRepository brandAliasRepository, BrandRepository brandRepository) {
        this.brandAliasRepository = brandAliasRepository;
        this.brandRepository = brandRepository;
    }

    public List<Brand> Search(String keyword) {
        keyword = KeywordProcess(keyword);
        List<Integer> brandIds = brandAliasRepository.findByBrandAliasName(keyword);

        List<Brand> brands = brandRepository.findBrandsByIds(brandIds);
        // 게임 리스트를 브랜드와 함께 로드하도록 설정
        brands.forEach(brand -> brand.setGames(brandRepository.findGamesByBrandId(brand.getBrandId())));
        return brands;
    }

    private String KeywordProcess(String keyword) {
        return GameService.getString(keyword);
    }
}
