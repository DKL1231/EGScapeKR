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
        List<Integer> BrandIds = brandAliasRepository.findByBrandAliasName(keyword);

        return brandRepository.findAllById(BrandIds);
    }

    private String KeywordProcess(String keyword) {
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(keyword, " ");
        sb.append("%");
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append("%");
        }
        return sb.toString();
    }
}
