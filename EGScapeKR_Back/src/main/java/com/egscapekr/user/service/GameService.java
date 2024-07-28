package com.egscapekr.user.service;

import com.egscapekr.user.dto.GameScoreDTO;
import com.egscapekr.user.entity.Game;
import com.egscapekr.user.entity.GameScore;
import com.egscapekr.user.repository.GameAliasRepository;
import com.egscapekr.user.repository.GameRepository;
import com.egscapekr.user.repository.GameScoreRepository;
import com.egscapekr.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    private final GameAliasRepository gameAliasRepository;
    private final GameRepository gameRepository;
    private final GameScoreRepository gameScoreRepository;
    private final UserRepository userRepository;

    public GameService(GameAliasRepository gameAliasRepository, GameRepository gameRepository, GameScoreRepository gameScoreRepository, UserRepository userRepository) {
        this.gameAliasRepository = gameAliasRepository;
        this.gameRepository = gameRepository;
        this.gameScoreRepository = gameScoreRepository;
        this.userRepository = userRepository;
    }

    public List<Game> Search(String keyword) {
        keyword = getString(keyword);
        List<Integer> gameIds = gameAliasRepository.findByGameAliasName(keyword);
        return gameRepository.findGamesByIdsWithBrand(gameIds);
    }

    public void addScore(String userId, GameScoreDTO gameScoreDTO) {
        GameScore gameScore = new GameScore();
        gameScore.setUser(userRepository.findByUsername(userId));
        gameScore.setGame(gameRepository.findGameByGameId(gameScoreDTO.getGameId()));
        gameScore.setScore(gameScoreDTO.getScore());
        gameScore.setTitle(gameScoreDTO.getTitle());
        gameScore.setContent(gameScoreDTO.getContent());
        gameScore.setVoteDate(gameScoreDTO.getVoteDate());
        gameScore.setScore(gameScoreDTO.getScore());

        gameScoreRepository.save(gameScore);
    }


    static String getString(String keyword) {
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
