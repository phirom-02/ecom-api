package com.firom.ecom_api.service;

import com.firom.ecom_api.dto.user.LoginUserDto;
import com.firom.ecom_api.dto.user.SignupUserDto;
import com.firom.ecom_api.model.User;
import com.firom.ecom_api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signup(SignupUserDto signupUserDto) {
        var user = new User();
        user.setUsername(signupUserDto.username());
        user.setPassword(passwordEncoder.encode(signupUserDto.password()));
        user.setEmail(signupUserDto.email());

        return userRepository.save(user);
    }

    public User login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.email(),
                        loginUserDto.password()
                )
        );

        return userRepository.findUserByEmail(loginUserDto.email())
                .orElseThrow(() -> new UsernameNotFoundException(loginUserDto.email()));
    }
}
