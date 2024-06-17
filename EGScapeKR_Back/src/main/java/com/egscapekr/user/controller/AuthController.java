package com.egscapekr.user.controller;

import com.egscapekr.user.entity.RefreshToken;
import com.egscapekr.user.jwt.JWTUtil;
import com.egscapekr.user.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public AuthController(JWTUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @PostMapping("/refresh")
    public String refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = getRefreshToken(request);
        if(jwtUtil.isTokenExpired(refreshToken, false)){
            logger.error("refresh token expired or Invalid");
            ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    //.secure(true) // HTTPS를 사용하는 경우 true로 설정
                    .path("/")
                    .maxAge(0) // 쿠키 즉시 만료
                    .sameSite("Strict") // 또는 "Lax" 혹은 "None"으로 설정
                    .build();

            // 응답에 쿠키 추가
            response.addHeader("Set-Cookie", cookie.toString());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        try {
            RefreshToken foundTokenInfo = refreshTokenRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new ApplicationContextException("Refresh Token Not Found"));

            String accessToken = foundTokenInfo.getAccessToken();
            String username = jwtUtil.getUsernameFromToken(accessToken);
            String role = jwtUtil.getRoleFromToken(accessToken);

            String newAccessToken = jwtUtil.createAccessToken(username, role, 60*60*1000L);
            String newRefreshToken = jwtUtil.createRefreshToken(60*60*24*5*1000L);

            response.addHeader("Authorization", "Bearer " + newAccessToken);
            Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setHttpOnly(true);
            // refreshTokenCookie.setSecure(true); // HTTPS 에서만 전송되도록 설정
            refreshTokenCookie.setMaxAge(60*60*24*5*1000);

            response.addCookie(refreshTokenCookie);
        }catch(ApplicationContextException e){
            logger.error("Refresh Token Not Found");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return null;
        }

        return "Token Refreshed";
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // RefreshToken 쿠키가 존재하지 않을 경우 null 반환
    }
}
