package com.egscapekr.user.controller;

import com.egscapekr.user.dto.MailDTO;
import com.egscapekr.user.dto.UserDTO;
import com.egscapekr.user.entity.EmailVerify;
import com.egscapekr.user.entity.User;
import com.egscapekr.user.jwt.JWTUtil;
import com.egscapekr.user.repository.EmailVerifyRepository;
import com.egscapekr.user.service.MailService;
import com.egscapekr.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@CrossOrigin(origins="http://localhost:5173")
@RequestMapping("/user")
public class UserController {
    private final JWTUtil jwtUtil;
    private final MailService mailService;
    private final UserService userService;
    private final EmailVerifyRepository emailVerifyRepository;

    public UserController(MailService mailService, UserService userService, JWTUtil jwtUtil, EmailVerifyRepository emailVerifyRepository) {
        this.mailService = mailService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.emailVerifyRepository = emailVerifyRepository;
    }

    @PostMapping("/getuserdata")
    public ResponseEntity<User> getNickname(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader("Authorization").split(" ")[1];
        try {
            User user = userService.findByUsername(jwtUtil.getUsernameFromToken(accessToken));
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/unamecheck")
    public String unameCheck(HttpServletResponse res, @RequestBody UserDTO userDTO){
        if(userService.isExistUsername(userDTO.getUsername())){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return "duplicated username";
        }
        return "unduplicated username";
    }

    @PostMapping("/emailcheck")
    public String emailCheck(HttpServletResponse res, @RequestBody UserDTO userDTO){
        if(userService.isExistEmail(userDTO.getEmail())){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return "duplicated email";
        }
        return "unduplicated email";
    }

    @PatchMapping("/resetpw")
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

    @PostMapping("/verifypass")
    public String verifyPassword(HttpServletRequest req, HttpServletResponse res, @RequestBody UserDTO userDTO) {
        String accessToken = req.getHeader("Authorization").split(" ")[1];
        try {
            if(userService.verifyPass(jwtUtil.getUsernameFromToken(accessToken), userDTO.getPassword())){
                res.setStatus(HttpServletResponse.SC_OK);
                return "password verified";
            }else{
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return "password verification failed";
            }
        }catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
    }

    @PatchMapping("/updatenickname")
    public String updateNickname(HttpServletRequest req, HttpServletResponse res, @RequestBody UserDTO userDTO) {
        String accessToken = req.getHeader("Authorization").split(" ")[1];
        try{
            userService.nicknameChange(jwtUtil.getUsernameFromToken(accessToken), userDTO.getNickname());
            return "nickname updated";
        }catch(Exception e){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
    }

    @PatchMapping("/updatepassword")
    public String updatePassword(HttpServletRequest req, HttpServletResponse res, @RequestBody UserDTO userDTO) {
        String accessToken = req.getHeader("Authorization").split(" ")[1];
        try{
            userService.passwordChange(jwtUtil.getUsernameFromToken(accessToken), userDTO.getPassword());
            return "password updated";
        }catch(Exception e){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
    }

    @PostMapping("/sendmail")
    public String sendVerifyMail(HttpServletRequest req, HttpServletResponse res, @RequestBody UserDTO userDTO){
        if(userService.isExistEmail(userDTO.getEmail())){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return "duplicated email";
        }

        String accessToken = req.getHeader("Authorization").split(" ")[1];

        MailDTO mailDTO = mailService.createVerificationMail(userDTO);
        System.out.println(userDTO.getVerifyCode());
        emailVerifyRepository.save(new EmailVerify(jwtUtil.getUsernameFromToken(accessToken), userDTO.getEmail(), userDTO.getVerifyCode()));
        mailService.sendMail(mailDTO);
        return "mail verified";
    }

    @PatchMapping("/updateemail")
    public String updateEmail(HttpServletRequest req, HttpServletResponse res, @RequestBody UserDTO userDTO) {
        String accessToken = req.getHeader("Authorization").split(" ")[1];
        try{
            if(verifyEmail(userDTO)){
                userService.emailChange(jwtUtil.getUsernameFromToken(accessToken), userDTO.getEmail());
                return "email updated";
            }else{
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                return null;
            }
        }catch (Exception e){
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        }
    }

    /*
     * 인증된 메일인지 판단하는 함수 입니다.
     */
    private Boolean verifyEmail(@RequestBody UserDTO userDTO){
        List<EmailVerify> verifyList =  emailVerifyRepository.findByVerifyCode(userDTO.getVerifyCode());
        for(EmailVerify emailVerify : verifyList){
            if(emailVerify.getEmail().equals(userDTO.getEmail())){
                return true;
            }
        }
        return false;
    }

    @DeleteMapping("/withdraw")
    public String withdraw(HttpServletRequest req, HttpServletResponse res) {
        String accessToken = req.getHeader("Authorization").split(" ")[1];
        userService.withdrawal(jwtUtil.getUsernameFromToken(accessToken));
        res.setStatus(HttpServletResponse.SC_OK);
        return "withdraw complete";
    }
}
