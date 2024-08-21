package com.egscapekr.user.service;

import com.egscapekr.user.dto.DiscussBrandAliasReqDTO;
import com.egscapekr.user.dto.DiscussBrandCreateReqDTO;
import com.egscapekr.user.dto.VoteDTO;
import com.egscapekr.user.entity.*;
import com.egscapekr.user.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussBrandService {
    private final BrandAliasRepository brandAliasRepository;
    private final DiscussBrandAliasRepository discussBrandAliasRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final DiscussBrandCreateRepository discussBrandCreateRepository;
    private final BrandAliasVoteRepository brandAliasVoteRepository;

    public DiscussBrandService(BrandAliasRepository brandAliasRepository, DiscussBrandAliasRepository discussBrandAliasRepository,
                               UserRepository userRepository, BrandRepository brandRepository,
                               DiscussBrandCreateRepository discussBrandCreateRepository,BrandAliasVoteRepository brandAliasVoteRepository) {
        this.brandAliasRepository = brandAliasRepository;
        this.discussBrandAliasRepository = discussBrandAliasRepository;
        this.userRepository = userRepository;
        this.brandRepository = brandRepository;
        this.discussBrandCreateRepository = discussBrandCreateRepository;
        this.brandAliasVoteRepository = brandAliasVoteRepository;
    }

    public void createBrandAliasDiscuss(DiscussBrandAliasReqDTO discussBrandAliasReqDTO){
        if(isExistAlias(discussBrandAliasReqDTO)){
            throw new DuplicateKeyException("중복된 별명입니다.");
        }
        DiscussBrandAlias discussBrandAlias = new DiscussBrandAlias(discussBrandAliasReqDTO);
        discussBrandAlias.setUser(userRepository.findByUsername(discussBrandAliasReqDTO.getUsername()));
        discussBrandAlias.setBrand(brandRepository.findBrandByBrandId(discussBrandAliasReqDTO.getBrandId()));
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

    public void createBrandCreateDiscuss(DiscussBrandCreateReqDTO discussBrandCreateReqDTO){
        if(isExistBrand(discussBrandCreateReqDTO)){
            throw new DuplicateKeyException("중복된 브랜드입니다.");
        }
        DiscussBrandCreate discussBrandCreate = new DiscussBrandCreate(discussBrandCreateReqDTO);
        discussBrandCreate.setUser(userRepository.findByUsername(discussBrandCreateReqDTO.getUsername()));
        discussBrandCreate.setDueTo(discussBrandCreateReqDTO.getCreateAt().plusDays(15));

        discussBrandCreateRepository.save(discussBrandCreate);
    }

    private boolean isExistBrand(DiscussBrandCreateReqDTO discussBrandCreateReqDTO){
        Brand brand = brandRepository.findBrandByBrandId(discussBrandCreateReqDTO.getBrandId());
        return brand != null;
    }

    public void voteBrandAliasDiscuss(VoteDTO voteDTO){
        DiscussBrandAlias discussBrandAlias = discussBrandAliasRepository.findByDiscussBrandAliasId(voteDTO.getDiscussId());
        User user = userRepository.findByUsername(voteDTO.getUsername());
        if(user == null || discussBrandAlias == null){
            throw new EntityNotFoundException();
        }
        BrandAliasVote brandAliasVote = brandAliasVoteRepository.findByDiscussBrandAliasAndUser(discussBrandAlias, user);
        if(brandAliasVote == null){
            brandAliasVote = new BrandAliasVote();
            brandAliasVote.setDiscussBrandAlias(discussBrandAlias);
            brandAliasVote.setUser(user);
        }else{
            if(brandAliasVote.isAgree()){
                discussBrandAlias.setAgree(discussBrandAlias.getAgree() - 1);
            }else{
                discussBrandAlias.setDisagree(discussBrandAlias.getDisagree() - 1);
            }
        }
        brandAliasVote.setAgree(voteDTO.isAgree());

        brandAliasVoteRepository.save(brandAliasVote);

        // TODO : 만약 Agree 가 일정 수 이상이고 Agree : Disagree 가 일정 비율 이상이면 Alias 추가
        if(brandAliasVote.isAgree()){
            discussBrandAlias.setAgree(discussBrandAlias.getAgree()+1);
        }else{
            discussBrandAlias.setDisagree(discussBrandAlias.getDisagree()+1);
        }

        discussBrandAliasRepository.save(discussBrandAlias);
    }
}
