package com.firom.ecom_api.module.user;

import com.firom.ecom_api.common.enums.ErrorCode;
import com.firom.ecom_api.common.exceptions.CustomRuntimeException;
import com.firom.ecom_api.module.user.dto.SaveUserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(SaveUserDto saveUserDto) {
        // Create a new User object
        User user = new User();
        user.setUsername(saveUserDto.username());
        user.setPassword(passwordEncoder.encode(saveUserDto.password()));
        user.setEmail(saveUserDto.email());
        user.setVerfied(false);

        // Save user
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException("No user found with username: " + username));
    }

    @Override
    public User verifyUser(User user) {
        if (user.isVerfied()) {
           throw new CustomRuntimeException(ErrorCode.BAD_REQUEST);
        }

        user.setVerfied(true);
        return userRepository.save(user);
    }
}
