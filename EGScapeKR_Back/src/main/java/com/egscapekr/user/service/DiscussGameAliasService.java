package com.egscapekr.user.service;

import com.egscapekr.user.dto.DiscussGameAliasReqDTO;
import com.egscapekr.user.entity.DiscussGameAlias;
import com.egscapekr.user.entity.GameAlias;
import com.egscapekr.user.repository.DiscussGameAliasRepository;
import com.egscapekr.user.repository.GameAliasRepository;
import com.egscapekr.user.repository.GameRepository;
import com.egscapekr.user.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussGameAliasService {

    private final DiscussGameAliasRepository discussGameAliasRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameAliasRepository gameAliasRepository;

    public DiscussGameAliasService(DiscussGameAliasRepository discussGameAliasRepository, UserRepository userRepository, GameRepository gameRepository, GameAliasRepository gameAliasRepository) {
        this.discussGameAliasRepository = discussGameAliasRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameAliasRepository = gameAliasRepository;
    }

    public void createGameDiscuss(DiscussGameAliasReqDTO discussGameAliasReqDTO){
        // TODO : 투표 제작자의 투표를 저장하는 Repository 와 Entity 를 생성해야 함
        if(isExistAlias(discussGameAliasReqDTO)){
            throw new DuplicateKeyException("중복된 별명입니다.");
        }
        DiscussGameAlias discussGameAlias = new DiscussGameAlias(discussGameAliasReqDTO);
        discussGameAlias.setUser(userRepository.findByUsername(discussGameAliasReqDTO.getUsername()));
        discussGameAlias.setGame(gameRepository.findGameByGameId(discussGameAliasReqDTO.getGameId()));
        discussGameAlias.setDueTo(discussGameAlias.getCreateAt().plusDays(15));

        discussGameAliasRepository.save(discussGameAlias);
    }

    public boolean isExistAlias(DiscussGameAliasReqDTO discussGameAliasReqDTO){
        List<GameAlias> gameAliasList = gameAliasRepository.findByGameId(discussGameAliasReqDTO.getGameId());
        for(GameAlias gameAlias : gameAliasList){
            if(gameAlias.getGameAliasName().replaceAll(" ","").equals(
                    discussGameAliasReqDTO.getGameAlias().replaceAll(" ", ""))){
                return true;
            }
        }
        return false;
    }

    public void voteGameDiscuss(){
        // TODO : 특정 토론에 투표하는 로직 작성
    }
}
