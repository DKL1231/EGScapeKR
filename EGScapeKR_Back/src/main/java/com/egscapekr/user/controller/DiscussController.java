package com.egscapekr.user.controller;

import com.egscapekr.user.dto.*;
import com.egscapekr.user.jwt.JWTUtil;
import com.egscapekr.user.service.DiscussBrandService;
import com.egscapekr.user.service.DiscussGameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/discuss")
public class DiscussController { // 게임/브랜드 또는 게임/브랜드의 별명 추가에 대한 토론 생성

    private final JWTUtil jwtUtil;
    private final DiscussGameService discussGameService;
    private final DiscussBrandService discussBrandService;

    public DiscussController(JWTUtil jwtUtil, DiscussGameService discussGameService, DiscussBrandService discussBrandService) {
        this.jwtUtil = jwtUtil;
        this.discussGameService = discussGameService;
        this.discussBrandService = discussBrandService;
    }

    private String getUsernameFromAccessToken(HttpServletRequest req){
        return jwtUtil.getUsernameFromToken(req.getHeader("Authorization").split(" ")[1]);
    }

    @PostMapping("/create/alias/game")
    public ResponseEntity<String> createGameAliasDiscuss(HttpServletRequest req, DiscussGameAliasReqDTO discussGameAliasReqDTO){
        String username = getUsernameFromAccessToken(req);
        discussGameAliasReqDTO.setUsername(username);
        try {
            discussGameService.createGameAliasDiscuss(discussGameAliasReqDTO);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Duplicate Alias", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Add Discuss Success", HttpStatus.OK);
    }

    @PostMapping("/create/create/game")
    public ResponseEntity<String> createGameCreateDiscuss(HttpServletRequest req, DiscussGameCreateReqDTO discussGameCreateReqDTO){
        String username = getUsernameFromAccessToken(req);
        discussGameCreateReqDTO.setUsername(username);
        try{
            discussGameService.createGameCreateDiscuss(discussGameCreateReqDTO);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Duplicate Game or No erogescape Id", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Add Discuss Success", HttpStatus.OK);
    }

    @PostMapping("/create/alias/brand")
    public ResponseEntity<String> createBrandAliasDiscuss(HttpServletRequest req, DiscussBrandAliasReqDTO discussBrandAliasReqDTO){
        String username = getUsernameFromAccessToken(req);
        discussBrandAliasReqDTO.setUsername(username);
        try{
            discussBrandService.createBrandAliasDiscuss(discussBrandAliasReqDTO);
        }catch (Exception e){
            return new ResponseEntity<>("Duplicate Brand", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Add Discuss Success", HttpStatus.OK);
    }

    @PostMapping("/create/create/brand")
    public ResponseEntity<String> createBrandCreateDiscuss(HttpServletRequest req, DiscussBrandCreateReqDTO discussBrandCreateReqDTO){
        String username = getUsernameFromAccessToken(req);
        discussBrandCreateReqDTO.setUsername(username);
        try{
            discussBrandService.createBrandCreateDiscuss(discussBrandCreateReqDTO);
        }catch(Exception e){
            return new ResponseEntity<>("Duplicate Brand", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Add Discuss Success", HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<String> voteDiscuss(HttpServletRequest req, VoteDTO voteDTO){
        // TODO : 특정 토론에 투표하는 로직 작성
        String username = getUsernameFromAccessToken(req);
        voteDTO.setUsername(username);

        String voteType = voteDTO.getType();
        try {
            if (voteType.equals("GA")) {
                discussGameService.voteGameAliasDiscuss(voteDTO);
            }else if (voteType.equals("GC")) {
                discussGameService.voteGameCreateDiscuss(voteDTO);
            }else if (voteType.equals("BA")){
                discussBrandService.voteBrandAliasDiscuss(voteDTO);
            }else if (voteType.equals("BC")){

            }
        }catch(Exception e){ // TODO: BusinessException 으로 수정
            return new ResponseEntity<>("Vote Failed", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Vote Success", HttpStatus.OK);
    }
}
