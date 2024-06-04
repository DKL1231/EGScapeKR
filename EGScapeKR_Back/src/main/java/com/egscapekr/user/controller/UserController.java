package com.egscapekr.user.controller;

import com.egscapekr.user.dto.MailDTO;
import com.egscapekr.user.dto.UserDTO;
import com.egscapekr.user.service.MailService;
import com.egscapekr.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class UserController {

    private final MailService mailService;
    private final UserService userService;

    public UserController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/user/resetpw")
    public String resetPassword(UserDTO userDTO) {
        if(userService.isExistData(userDTO)){
            MailDTO mailDTO = mailService.createResetMail(userDTO);
            userService.passwordReset(userDTO);
            mailService.sendMail(mailDTO);
            return "reset complete";
        }
        return "reset failed";
    }
}
