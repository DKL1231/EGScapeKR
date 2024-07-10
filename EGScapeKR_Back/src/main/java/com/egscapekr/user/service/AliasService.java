package com.egscapekr.user.service;

import com.egscapekr.user.dto.BrandDTO;
import com.egscapekr.user.dto.GameDTO;
import com.egscapekr.user.entity.BrandAlias;
import com.egscapekr.user.entity.GameAlias;
import com.egscapekr.user.repository.BrandAliasRepository;
import com.egscapekr.user.repository.GameAliasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {
    private final GameAliasRepository gameAliasRepository;
    private final BrandAliasRepository brandAliasRepository;
    public AliasService(GameAliasRepository gameAliasRepository, BrandAliasRepository brandAliasRepository) {
        this.gameAliasRepository = gameAliasRepository;
        this.brandAliasRepository = brandAliasRepository;
    }

    public void addGameAlias(GameDTO gameDTO) {
        GameAlias gameAlias = new GameAlias();

        gameAlias.setGameId(gameDTO.getGameId());
        gameAlias.setGameAliasName(gameDTO.getGameAliasName());

        if(!checkDuplicateGameAlias(gameAlias)) {
            gameAliasRepository.save(gameAlias);
        }
    }

    private boolean checkDuplicateGameAlias(GameAlias gameAlias) {
        List<GameAlias> list = gameAliasRepository.findByGameId(gameAlias.getGameId());
        for (GameAlias gameAlias1 : list) {
            if(gameAlias1.getGameAliasName().equals(gameAlias.getGameAliasName())) {
                return true;
            }
        }
        return false;
    }

    public void addBrandAlias(BrandDTO brandDTO) {
        BrandAlias brandAlias = new BrandAlias();
        brandAlias.setBrandId(brandDTO.getBrandId());
        brandAlias.setAlias(brandDTO.getAlias());

        if(!checkDuplicateBrandAlias(brandAlias)) {
            brandAliasRepository.save(brandAlias);
        }
    }

    private boolean checkDuplicateBrandAlias(BrandAlias brandAlias) {
        List<BrandAlias> list = brandAliasRepository.findByBrandId(brandAlias.getBrandId());
        for (BrandAlias brandAlias1 : list) {
            if(brandAlias1.getAlias().equals(brandAlias.getAlias())) {
                return true;
            }
        }
        return false;
    }
}
