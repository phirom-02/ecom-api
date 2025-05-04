package com.firom.ecom_api.module.user;

import com.firom.ecom_api.module.user.dto.SaveUserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User saveUser(SaveUserDto saveUserDto);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User verifyUser(User user);
}
