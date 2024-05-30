package com.egscapekr.user.service;

import com.egscapekr.user.dto.UserDTO;
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

    public void joinProcess(UserDTO userDTO){

        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String nickname = userDTO.getNickname();
        String email = userDTO.getEmail();

        Boolean isExistUsername = userRepository.existsByUsername(username);
        Boolean isExistEmail = userRepository.existsByEmail(email);
        if(isExistUsername||isExistEmail){
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
