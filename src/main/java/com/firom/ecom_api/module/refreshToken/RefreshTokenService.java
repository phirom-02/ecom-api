package com.firom.ecom_api.module.refreshToken;

import com.firom.ecom_api.module.product.RefreshToken;
import com.firom.ecom_api.module.user.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken findRefreshTokenByToken(String token);

    RefreshToken rotateRefreshToken(RefreshToken oldToken, User user);

    void revokeToken(RefreshToken token);

    void revokeAllByUser(User user);
}
