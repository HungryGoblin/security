package ru.geekbrains.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public String encodePassword (String password) {
        return passwordEncoder.encode(password);
    }

}
