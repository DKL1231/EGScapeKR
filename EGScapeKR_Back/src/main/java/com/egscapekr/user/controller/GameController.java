package com.egscapekr.user.controller;

import com.egscapekr.user.dto.GameDTO;
import com.egscapekr.user.dto.GameDetailDTO;
import com.egscapekr.user.dto.GameScoreDTO;
import com.egscapekr.user.entity.Game;
import com.egscapekr.user.jwt.JWTUtil;
import com.egscapekr.user.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/game")
public class GameController {
    // /game/about : Link format that non-login users also allow
    // /game/~~ : Link format allowed only by logged-in users

    private final GameService gameService;
    private final JWTUtil jwtUtil;

    public GameController(GameService gameService, JWTUtil jwtUtil) {
        this.gameService = gameService;
        this.jwtUtil = jwtUtil;
    }

    /**
    * API to perform game search.
     */
    @GetMapping("/about/search")
    public ResponseEntity<List<Game>> Search(@RequestParam("keyword") String keyword) {
        List<Game> games = gameService.Search(keyword);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/about/detail/{gameId}")
    public ResponseEntity<GameDetailDTO> GetGameDetail(@PathVariable int gameId) {
        return new ResponseEntity<>(gameService.getGameDetail(gameId), HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<String> VoteScore(HttpServletRequest req, @RequestBody GameScoreDTO gameScoreDTO) {
        String accessToken = req.getHeader("Authorization").split(" ")[1];
        gameService.addScore(jwtUtil.getUsernameFromToken(accessToken), gameScoreDTO);
        return new ResponseEntity<String>("vote score success", HttpStatus.OK);
    }
}
