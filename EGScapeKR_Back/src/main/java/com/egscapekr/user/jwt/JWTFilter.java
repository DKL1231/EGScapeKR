package com.egscapekr.user.jwt;

import com.egscapekr.user.dto.CustomUserDetails;
import com.egscapekr.user.entity.RefreshToken;
import com.egscapekr.user.entity.User;
import com.egscapekr.user.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public JWTFilter(JWTUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        logger.info("authorizationHeader: {}", authorizationHeader);
        // header 검증
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.info("token null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.split(" ")[1];
        if(jwtUtil.isTokenExpired(token, true)){
            logger.error("Access token expired or Invalid");

            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsernameFromToken(token);
        String role = jwtUtil.getRoleFromToken(token);

        createToken(username, role);

        filterChain.doFilter(request, response);
    }

    private void createToken(String username, String role){
        User user = new User();

        user.setUsername(username);
        user.setRole(role);
        user.setPassword("temppassword");
        user.setEmail("tempemail@gmail.com");
        user.setNickname("tempnickname");

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
