package com.egscapekr.user.service;

import com.egscapekr.user.dto.MailDTO;
import com.egscapekr.user.dto.UserDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class MailService {

    private final char[] charSet;
    @Autowired
    private JavaMailSender mailSender;


    public MailService() {
        this.charSet = new char[62];
        for(int i = 0; i < 26; i++) {
            this.charSet[i] = (char)('a' + i);
        }
        for(int i = 0; i < 26; i++) {
            this.charSet[i+26] = (char)('A' + i);
        }
        for(int i = 0; i <= 9; i++) {
            this.charSet[i+52] = (char)('0' + i);
        }
    }

    public MailDTO createResetMail(UserDTO user) {
        String userEmail = user.getEmail();

        MailDTO mailDTO = new MailDTO();
        mailDTO.setAddress(userEmail);
        mailDTO.setTitle("ErogameScape-KR 비밀번호 초기화 안내 이메일입니다.");
        String tempPass = getTemporaryPass();
        mailDTO.setMessage("ErogameScape-KR 비밀번호 초기화 안내 이메일입니다.\n"
            +"회원님의 임시 비밀번호는 "+tempPass+" 입니다.\n"
            +"로그인 후 비밀번호 변경을 해주시기 바랍니다."
        );
        user.setPassword(tempPass);
        return mailDTO;
    }

    public String getTemporaryPass(){
        StringBuilder sb = new StringBuilder();
        long seed = System.nanoTime();
        Random random = new Random(seed);

        for(int i = 0; i<15; i++){
            int randomIdx = random.nextInt(62);
            sb.append(charSet[randomIdx]);
        }
        return sb.toString();
    }

    public void sendMail(MailDTO mailDTO) {
        log.info("Send Mail to "+mailDTO.getAddress());
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailDTO.getAddress());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());

        mailSender.send(message);
    }
}
