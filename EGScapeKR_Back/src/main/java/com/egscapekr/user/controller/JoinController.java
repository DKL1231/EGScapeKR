package com.egscapekr.user.controller;

import com.egscapekr.user.dto.MailDTO;
import com.egscapekr.user.dto.UserDTO;
import com.egscapekr.user.entity.EmailVerify;
import com.egscapekr.user.repository.EmailVerifyRepository;
import com.egscapekr.user.service.JoinService;
import com.egscapekr.user.service.MailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.http.HttpResponse;
import java.util.List;

@Controller
@ResponseBody
public class JoinController {

    private final JoinService joinService;
    private final MailService mailService;
    private final EmailVerifyRepository emailVerifyRepository;

    public JoinController(JoinService joinService, MailService mailService, EmailVerifyRepository emailVerifyRepository) {
        this.joinService = joinService;
        this.mailService = mailService;
        this.emailVerifyRepository = emailVerifyRepository;
    }

    @PostMapping("/join")
    public String joinProcess(HttpServletResponse res, @RequestBody UserDTO userDTO){
        if(verifyEmail(userDTO)) {
            joinService.joinProcess(userDTO);
            return "join success";
        }
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return "join failed";
    }

    /*
    * 인증 메일을 전송하는 API 입니다.
    * */
    @PostMapping("/join/email")
    public String joinEmail(@RequestBody UserDTO userDTO){
        if(joinService.checkDuplication(userDTO)){
            return "duplicate userName or Email";
        }
        System.out.println(userDTO.getEmail());
        MailDTO mailDTO = mailService.createVerificationMail(userDTO);
        System.out.println(userDTO.getVerifyCode());
        emailVerifyRepository.save(new EmailVerify(userDTO.getUsername(), userDTO.getEmail(), userDTO.getVerifyCode()));
        mailService.sendMail(mailDTO);
        return "ok";
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
}
