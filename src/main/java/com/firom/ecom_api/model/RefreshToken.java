package com.firom.ecom_api.model;

import com.firom.ecom_api.common.enums.TokenStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY) // Allow multiple tokens per user/device
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    // TODO: add more properties including ip, device...
}
