package com.egscapekr.user.controller;

import com.egscapekr.user.dto.MailDTO;
import com.egscapekr.user.dto.UserDTO;
import com.egscapekr.user.entity.User;
import com.egscapekr.user.jwt.JWTUtil;
import com.egscapekr.user.service.MailService;
import com.egscapekr.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@CrossOrigin(origins="http://localhost:5173")
public class UserController {
    private final JWTUtil jwtUtil;
    private final MailService mailService;
    private final UserService userService;

    public UserController(MailService mailService, UserService userService, JWTUtil jwtUtil) {
        this.mailService = mailService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user/getnickname")
    public String getNickname(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader("Authorization").split(" ")[1];
        try {
            User user = userService.findByUsername(jwtUtil.getUsernameFromToken(accessToken));

            return user.getNickname();
        }catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/user/unamecheck")
    public String unameCheck(HttpServletResponse res, @RequestBody UserDTO userDTO){
        if(userService.isExistUsername(userDTO.getUsername())){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return "duplicated username";
        }
        return "unduplicated username";
    }

    @PostMapping("/user/emailcheck")
    public String emailCheck(HttpServletResponse res, @RequestBody UserDTO userDTO){
        if(userService.isExistEmail(userDTO.getEmail())){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return "duplicated email";
        }
        return "unduplicated email";
    }

    @PostMapping("/user/resetpw")
    public String resetPassword(HttpServletResponse res, @RequestBody UserDTO userDTO) {
        if(userService.isExistData(userDTO)){
            MailDTO mailDTO = mailService.createResetMail(userDTO);
            userService.passwordReset(userDTO);
            mailService.sendMail(mailDTO);
            return "reset complete";
        }
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return "reset failed";
    }
}
