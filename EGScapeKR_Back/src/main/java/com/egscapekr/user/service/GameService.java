package com.egscapekr.user.service;

import com.egscapekr.user.dto.GameDetailDTO;
import com.egscapekr.user.dto.GameScoreDTO;
import com.egscapekr.user.entity.Game;
import com.egscapekr.user.entity.GameAlias;
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

    public GameDetailDTO getGameDetail(int gameId) {
        Game game = gameRepository.findGameByGameId(gameId);
        List<GameAlias> aliasList = gameAliasRepository.findByGameId(gameId);
        GameDetailDTO gameDetailDTO = new GameDetailDTO();

        List<String> gameAliasNameList = new ArrayList<>();
        for (GameAlias gameAlias : aliasList) {
            gameAliasNameList.add(gameAlias.getGameAliasName());
        }

        gameDetailDTO.setGameAliasList(gameAliasNameList);
        gameDetailDTO.setGameNameOrigin(game.getGameNameOrigin());
        gameDetailDTO.setSellDay(game.getSellDay());
        gameDetailDTO.setCount(game.getCount());
        gameDetailDTO.setMedian(game.getMedian());
        gameDetailDTO.setGetchu(game.getGetchu());
        gameDetailDTO.setEroscapeCount(game.getEroscapeCount());
        gameDetailDTO.setEroscapeMedian(game.getEroscapeMedian());
        gameDetailDTO.setEroscapeUpdateDay(game.getEroscapeUpdateDay());
        gameDetailDTO.setUrl(game.getUrl());
        gameDetailDTO.setModel(game.getModel());
        gameDetailDTO.setErogame(game.getErogame());
        gameDetailDTO.setGenre(game.getGenre());
        gameDetailDTO.setTwitter(game.getTwitter());
        gameDetailDTO.setVndb(game.getVndb());
        gameDetailDTO.setBrandId(game.getBrand().getBrandId());
        gameDetailDTO.setBrandName(game.getBrand().getBrandNameTrans());

        return gameDetailDTO;
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
