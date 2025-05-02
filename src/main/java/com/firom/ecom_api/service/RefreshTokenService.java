package com.firom.ecom_api.service;

import com.firom.ecom_api.model.RefreshToken;
import com.firom.ecom_api.model.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken findRefreshTokenByToken(String token);

    RefreshToken rotateRefreshToken(RefreshToken oldToken, User user);

    void revokeToken(RefreshToken token);

    void revokeAllByUser(User user);
}
