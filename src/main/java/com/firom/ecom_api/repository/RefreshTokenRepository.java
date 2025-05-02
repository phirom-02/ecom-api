package com.firom.ecom_api.repository;

import com.firom.ecom_api.model.RefreshToken;
import com.firom.ecom_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByUser(User user);

    void deleteByUser(User user);
}