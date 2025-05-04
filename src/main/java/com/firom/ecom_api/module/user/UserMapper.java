package com.firom.ecom_api.module.user;

public class UserMapper {
    public static CustomUserDetails usertoCustomUserDetails(User user) {
        return new CustomUserDetails(user);
    }
}
