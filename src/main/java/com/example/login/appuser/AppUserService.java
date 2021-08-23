package com.example.login.appuser;

import com.example.login.registration.RegistrationRequestService;
import com.example.login.registration.token.ConfirmationToken;
import com.example.login.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private static final String USER_NOT_FOUND =
            "User with %s not found";
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND,email)));
    }

    public String signUp(AppUser appUser){
        boolean userExist = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();

        if(userExist){
            throw new IllegalStateException("Email already token");
        }
        String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        System.out.println(encodePassword);
        appUser.setPassword(encodePassword);
        appUserRepository.save(appUser);
        String token = UUID.randomUUID().toString();
        // TO DO: Send confirmation token
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        //TO DO:Send email
        return token;
    }

    public int enabledAppUser(String email){
        return appUserRepository.enableAppUser(email);
    }
}
