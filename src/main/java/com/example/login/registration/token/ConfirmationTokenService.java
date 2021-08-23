package com.example.login.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    @Autowired
    ConfirmTokenRepository confirmTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmTokenRepository.findByToken(token);
    }

    public int setConfirmAt(String token){
        return confirmTokenRepository.updateConfirmAt(token, LocalDateTime.now());
    }
}
