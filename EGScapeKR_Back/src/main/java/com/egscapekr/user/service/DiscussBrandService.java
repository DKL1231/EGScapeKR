package com.egscapekr.user.service;

import com.egscapekr.user.dto.DiscussBrandAliasReqDTO;
import com.egscapekr.user.entity.BrandAlias;
import com.egscapekr.user.entity.DiscussBrandAlias;
import com.egscapekr.user.repository.BrandAliasRepository;
import com.egscapekr.user.repository.BrandRepository;
import com.egscapekr.user.repository.DiscussBrandAliasRepository;
import com.egscapekr.user.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussBrandService {
    private final BrandAliasRepository brandAliasRepository;
    private final DiscussBrandAliasRepository discussBrandAliasRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;

    public DiscussBrandService(BrandAliasRepository brandAliasRepository, DiscussBrandAliasRepository discussBrandAliasRepository,
                               UserRepository userRepository, BrandRepository brandRepository) {
        this.brandAliasRepository = brandAliasRepository;
        this.discussBrandAliasRepository = discussBrandAliasRepository;
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
    }

    public void createBrandAliasDiscuss(DiscussBrandAliasReqDTO discussBrandAliasReqDTO){
        if(isExistAlias(discussBrandAliasReqDTO)){
            throw new DuplicateKeyException("중복된 별명입니다.");
        }
        DiscussBrandAlias discussBrandAlias = new DiscussBrandAlias(discussBrandAliasReqDTO);
        discussBrandAlias.setUser(userRepository.findByUsername(discussBrandAliasReqDTO.getUsername()));
        discussBrandAlias.setBrand(brandRepository.findBrandById(discussBrandAliasReqDTO.getBrandId()));
        discussBrandAlias.setDueTo(discussBrandAlias.getCreateAt().plusDays(15));

        discussBrandAliasRepository.save(discussBrandAlias);

    }

    private boolean isExistAlias(DiscussBrandAliasReqDTO discussBrandAliasReqDTO){
        List<BrandAlias> brandAliasList = brandAliasRepository.findByBrandId(discussBrandAliasReqDTO.getBrandId());
        for(BrandAlias brandAlias : brandAliasList){
            if(brandAlias.getBrandAliasName().replaceAll(" ", "").equals(
                    discussBrandAliasReqDTO.getBrandAlias().replaceAll(" ","")
            ))
                return true;
        }
        return false;
    }
}
