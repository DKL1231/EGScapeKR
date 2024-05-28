package com.egscapekr.user.jwt;

import com.egscapekr.user.dto.CustomUserDetails;
import com.egscapekr.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        // header 검증
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            logger.info("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorizationHeader.split(" ")[1];

        if(jwtUtil.isTokenExpired(token)){
            logger.info("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String username = jwtUtil.getUsernameFromToken(token);
        String role = jwtUtil.getRoleFromToken(token);

        User user = new User();

        user.setUsername(username);
        user.setRole(role);
        user.setPassword("temppassword");
        user.setEmail("tempemail@gmail.com");
        user.setNickname("tempnickname");

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
