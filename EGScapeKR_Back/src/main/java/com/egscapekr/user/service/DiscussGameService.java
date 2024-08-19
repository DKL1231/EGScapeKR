package com.egscapekr.user.service;

import com.egscapekr.user.dto.DiscussGameAliasReqDTO;
import com.egscapekr.user.dto.DiscussGameCreateReqDTO;
import com.egscapekr.user.dto.VoteDTO;
import com.egscapekr.user.entity.*;
import com.egscapekr.user.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussGameService {

    private final DiscussGameAliasRepository discussGameAliasRepository;
    private final DiscussGameCreateRepository discussGameCreateRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameAliasRepository gameAliasRepository;
    private final GameAliasVoteRepository gameAliasVoteRepository;
    private final GameCreateVoteRepository gameCreateVoteRepository;

    public DiscussGameService(DiscussGameAliasRepository discussGameAliasRepository, DiscussGameCreateRepository discussGameCreateRepository, UserRepository userRepository, GameRepository gameRepository, GameAliasRepository gameAliasRepository, GameAliasVoteRepository gameAliasVoteRepository, GameCreateVoteRepository gameCreateVoteRepository) {
        this.discussGameAliasRepository = discussGameAliasRepository;
        this.discussGameCreateRepository = discussGameCreateRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.gameAliasRepository = gameAliasRepository;
        this.gameAliasVoteRepository = gameAliasVoteRepository;
        this.gameCreateVoteRepository = gameCreateVoteRepository;
    }

    public void createGameAliasDiscuss(DiscussGameAliasReqDTO discussGameAliasReqDTO){
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

    public void createGameCreateDiscuss(DiscussGameCreateReqDTO discussGameCreateReqDTO){
        if(isExistGame(discussGameCreateReqDTO)){
            throw new DuplicateKeyException("중복된 게임이거나 erogescapeId가 존재하지 않습니다.");
        }
        DiscussGameCreate discussGameCreate = new DiscussGameCreate(discussGameCreateReqDTO);
        discussGameCreate.setUser(userRepository.findByUsername(discussGameCreateReqDTO.getUsername()));
        discussGameCreate.setDueTo(discussGameCreateReqDTO.getCreateAt().plusDays(15));

        discussGameCreateRepository.save(discussGameCreate);
    }

    public boolean isExistGame(DiscussGameCreateReqDTO discussGameCreateReqDTO){
        Game game = gameRepository.findGameByGameId(discussGameCreateReqDTO.getGameId());
        return game != null;
    }


    public void voteGameAliasDiscuss(VoteDTO voteDTO){
        // TODO : BusinessException 작성
        DiscussGameAlias discussGameAlias = discussGameAliasRepository.findByDiscussGameAliasId(voteDTO.getDiscussId());
        User user = userRepository.findByUsername(voteDTO.getUsername());
        if(user == null || discussGameAlias == null){
            throw new EntityNotFoundException();
        }
        GameAliasVote gameAliasVote = gameAliasVoteRepository.findByDiscussGameAliasAndUser(discussGameAlias, user);
        if(gameAliasVote == null) {
            gameAliasVote = new GameAliasVote();
            gameAliasVote.setDiscussGameAlias(discussGameAlias);
            gameAliasVote.setUser(user);
        }else{ // 이미 투표한 내용이 있다면
            if(gameAliasVote.isAgree()){
                discussGameAlias.setAgree(discussGameAlias.getAgree()-1);
            }else{
                discussGameAlias.setDisagree(discussGameAlias.getDisagree()-1);
            }
        }
        gameAliasVote.setAgree(voteDTO.isAgree());

        gameAliasVoteRepository.save(gameAliasVote);

        // TODO : 만약 Agree 가 일정 수 이상이고 Agree : Disagree 가 일정 비율 이상이면 Alias 추가
        if(gameAliasVote.isAgree()){
            discussGameAlias.setAgree(discussGameAlias.getAgree()+1);
        }else{
            discussGameAlias.setDisagree(discussGameAlias.getDisagree()+1);
        }

        discussGameAliasRepository.save(discussGameAlias);
    }

    public void voteGameCreateDiscuss(VoteDTO voteDTO){
        // TODO : BusinessException 작성
        DiscussGameCreate discussGameCreate = discussGameCreateRepository.findByDiscussGameCreateId(voteDTO.getDiscussId());
        User user = userRepository.findByUsername(voteDTO.getUsername());
        if(user == null || discussGameCreate == null){
            throw new EntityNotFoundException();
        }
        GameCreateVote gameCreateVote = gameCreateVoteRepository.findByDiscussGameCreateAndUser(discussGameCreate, user);
        if(gameCreateVote == null) {
            gameCreateVote = new GameCreateVote();
            gameCreateVote.setDiscussGameCreate(discussGameCreate);
            gameCreateVote.setUser(user);
        }else{ // 이미 투표한 내용이 있다면
            if(gameCreateVote.isAgree()){
                discussGameCreate.setAgree(discussGameCreate.getAgree()-1);
            }else{
                discussGameCreate.setDisagree(discussGameCreate.getDisagree()-1);
            }
        }
        gameCreateVote.setAgree(gameCreateVote.isAgree());

        gameCreateVoteRepository.save(gameCreateVote);

        // TODO : 만약 Agree 가 일정 수 이상이고 Agree : Disagree 가 일정 비율 이상이면 Create
        if(gameCreateVote.isAgree()){
            discussGameCreate.setAgree(discussGameCreate.getAgree()+1);
        }else{
            discussGameCreate.setDisagree(discussGameCreate.getDisagree()+1);
        }

        discussGameCreateRepository.save(discussGameCreate);
    }
}
