package com.egscapekr.user.service;

import com.egscapekr.user.entity.Game;
import com.egscapekr.user.repository.GameAliasRepository;
import com.egscapekr.user.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final GameAliasRepository gameAliasRepository;
    private final GameRepository gameRepository;

    public GameService(GameAliasRepository gameAliasRepository, GameRepository gameRepository) {
        this.gameAliasRepository = gameAliasRepository;
        this.gameRepository = gameRepository;
    }

    public List<Game> Search(String keyword) {
        keyword = KeywordProcess(keyword);
        List<Integer> gameIds = gameAliasRepository.findByGameAliasName(keyword);
        return gameRepository.findGamesByIdsWithBrand(gameIds);
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
