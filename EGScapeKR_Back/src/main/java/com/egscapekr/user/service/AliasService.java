package com.egscapekr.user.service;

import com.egscapekr.user.dto.BrandDTO;
import com.egscapekr.user.dto.GameDTO;
import com.egscapekr.user.entity.Brand;
import com.egscapekr.user.entity.BrandAlias;
import com.egscapekr.user.entity.Game;
import com.egscapekr.user.entity.GameAlias;
import com.egscapekr.user.repository.BrandAliasRepository;
import com.egscapekr.user.repository.BrandRepository;
import com.egscapekr.user.repository.GameAliasRepository;
import com.egscapekr.user.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliasService {
    private final GameAliasRepository gameAliasRepository;
    private final BrandAliasRepository brandAliasRepository;
    private final GameRepository gameRepository;
    private final BrandRepository brandRepository;

    public AliasService(GameAliasRepository gameAliasRepository, BrandAliasRepository brandAliasRepository, GameRepository gameRepository, BrandRepository brandRepository) {
        this.gameAliasRepository = gameAliasRepository;
        this.brandAliasRepository = brandAliasRepository;
        this.gameRepository = gameRepository;
        this.brandRepository = brandRepository;
    }

    public void addGameAlias(GameDTO gameDTO) {
        GameAlias gameAlias = new GameAlias();

        Game game = gameRepository.findById(gameDTO.getGameId()).orElseThrow(() -> new RuntimeException("Game not found")); // 추가

        gameAlias.setGame(game);
        gameAlias.setGameAliasName(gameDTO.getGameAliasName());

        if(!checkDuplicateGameAlias(gameAlias)) {
            gameAliasRepository.save(gameAlias);
        }
    }

    private boolean checkDuplicateGameAlias(GameAlias gameAlias) {
        List<GameAlias> list = gameAliasRepository.findByGameId(gameAlias.getGame().getGameId());
        for (GameAlias gameAlias1 : list) {
            if(gameAlias1.getGameAliasName().equals(gameAlias.getGameAliasName())) {
                return true;
            }
        }
        return false;
    }

    public void addBrandAlias(BrandDTO brandDTO) {
        BrandAlias brandAlias = new BrandAlias();

        Brand brand = brandRepository.findById(brandDTO.getBrandId()).orElseThrow(() -> new RuntimeException("Brand not found")); // 추가

        brandAlias.setBrand(brand);
        brandAlias.setBrandAliasName(brandDTO.getAlias());

        if(!checkDuplicateBrandAlias(brandAlias)) {
            brandAliasRepository.save(brandAlias);
        }
    }

    private boolean checkDuplicateBrandAlias(BrandAlias brandAlias) {
        List<BrandAlias> list = brandAliasRepository.findByBrandId(brandAlias.getBrand().getBrandId());
        for (BrandAlias brandAlias1 : list) {
            if(brandAlias1.getBrandAliasName().equals(brandAlias.getBrandAliasName())) {
                return true;
            }
        }
        return false;
    }
}
