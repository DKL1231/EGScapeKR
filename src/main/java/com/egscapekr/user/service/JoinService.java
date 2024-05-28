package com.egscapekr.user.service;

import com.egscapekr.user.dto.JoinDTO;
import com.egscapekr.user.entity.User;
import com.egscapekr.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO){

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String nickname = joinDTO.getNickname();
        String email = joinDTO.getEmail();

        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist){
            return;
        }

        User data = new User();


        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setNickname(nickname);
        data.setEmail(email);
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
