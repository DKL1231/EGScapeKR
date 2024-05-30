package com.egscapekr.user.service;

import com.egscapekr.user.dto.UserDTO;
import com.egscapekr.user.entity.User;
import com.egscapekr.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean isExistData(UserDTO userDTO){
        return userRepository.existsByUsername(userDTO.getUsername()) && userRepository.existsByEmail(userDTO.getEmail());
    }

    public void passwordReset(UserDTO userDTO){
        User user = userRepository.findByUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
    }
}
