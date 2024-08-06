package com.egscapekr.user.controller;

import com.egscapekr.user.dto.DiscussGameAliasReqDTO;
import com.egscapekr.user.jwt.JWTUtil;
import com.egscapekr.user.service.DiscussGameAliasService;
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
    private final DiscussGameAliasService discussGameAliasService;

    public DiscussController(JWTUtil jwtUtil, DiscussGameAliasService discussGameAliasService) {
        this.jwtUtil = jwtUtil;
        this.discussGameAliasService = discussGameAliasService;
    }

    private String getUsernameFromAccessToken(HttpServletRequest req){
        return jwtUtil.getUsernameFromToken(req.getHeader("Authorization").split(" ")[1]);
    }

    @PostMapping("/create/game")
    public ResponseEntity<String> createGameDiscuss(HttpServletRequest req, DiscussGameAliasReqDTO discussGameAliasReqDTO){
        String username = getUsernameFromAccessToken(req);
        discussGameAliasReqDTO.setUsername(username);
        try {
            discussGameAliasService.createGameDiscuss(discussGameAliasReqDTO);
        }catch(DuplicateKeyException e){
            return new ResponseEntity<>("Duplicate Alias", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Add Discuss Success", HttpStatus.OK);
    }

    @PostMapping("/vote/game")
    public ResponseEntity<String> voidGameDiscuss(HttpServletRequest req){
        // TODO : 특정 토론에 투표하는 로직 작성
        return null;
    }
}
