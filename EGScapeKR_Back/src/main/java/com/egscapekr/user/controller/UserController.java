package com.egscapekr.user.controller;

import com.egscapekr.user.dto.MailDTO;
import com.egscapekr.user.dto.UserDTO;
import com.egscapekr.user.service.MailService;
import com.egscapekr.user.service.UserService;
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

    private final MailService mailService;
    private final UserService userService;

    public UserController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/user/resetpw")
    public String resetPassword(HttpServletResponse res, @RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getEmail());
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
