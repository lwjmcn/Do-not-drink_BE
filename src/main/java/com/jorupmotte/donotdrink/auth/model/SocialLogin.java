package com.jorupmotte.donotdrink.auth.model;

import com.jorupmotte.donotdrink.common.type.SocialLoginType;
import com.jorupmotte.donotdrink.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_login")
@Getter
@NoArgsConstructor
public class SocialLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "token_id", unique = true, nullable = false, length = 45)
    private String tokenId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private SocialLoginType provider;

    public SocialLogin(User user, String tokenId, SocialLoginType provider) {
        this.user = user;
        this.tokenId = tokenId;
        this.provider = provider;
    }

}