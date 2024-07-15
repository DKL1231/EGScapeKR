package com.egscapekr.user.controller;

import com.egscapekr.user.entity.Game;
import com.egscapekr.user.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/game")
public class GameController {
    // /game/about : Link format that non-login users also allow
    // /game/~~ : Link format allowed only by logged-in users

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
    * API to perform game search.
     */
    @GetMapping("/about/search")
    public ResponseEntity<List<Game>> Search(@RequestParam("keyword") String keyword) {
        List<Game> games = gameService.Search(keyword);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}
