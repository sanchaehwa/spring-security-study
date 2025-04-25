package com.example.testsecurity.service;

import com.example.testsecurity.dto.JoinDTO;
import com.example.testsecurity.entity.UserEntity;
import com.example.testsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void joinProcess(JoinDTO joinDTO) {
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        //중복 사용자가 존재하는경우
        if (isUser) {
            System.out.println("이미 존재하는 사용자입니다.");
            return;
        }

        //존재하지않는 경우
        UserEntity data = new UserEntity();
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setRole("ROLE_USER");
        userRepository.save(data);
    }

}
