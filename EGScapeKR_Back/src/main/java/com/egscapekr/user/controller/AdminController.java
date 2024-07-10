package com.egscapekr.user.controller;
import com.egscapekr.user.dto.BrandDTO;
import com.egscapekr.user.dto.GameDTO;
import com.egscapekr.user.entity.GameAlias;
import com.egscapekr.user.service.AliasService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/admin")
public class AdminController {

    private final AliasService aliasService;

    public AdminController(AliasService aliasService) {
        this.aliasService = aliasService;
    }

    @GetMapping("/")
    public String adminP() {

        return "admin Controller";
    }

    @PostMapping("/alias/game")
    public String adminAliasGame(HttpServletRequest request, HttpServletResponse response, GameDTO gameDTO) {
        aliasService.addGameAlias(gameDTO);
        return "game alias";
    }

    @PostMapping("/alias/brand")
    public String adminAliasBrand(HttpServletRequest request, HttpServletResponse response, BrandDTO brandDTO) {
        aliasService.addBrandAlias(brandDTO);
        return "brand alias";
    }

}