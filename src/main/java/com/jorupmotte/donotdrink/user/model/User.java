package com.jorupmotte.donotdrink.user.model;

import com.jorupmotte.donotdrink.auth.dto.request.SignUpRequestDto;
import com.jorupmotte.donotdrink.theme.model.Theme;
import com.jorupmotte.donotdrink.common.type.LoginType;
import com.jorupmotte.donotdrink.common.type.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="account_id", nullable = false, unique = true, length = 45)
    private String accountId;

    @Column(name = "nickname", nullable = false, length = 45)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

//    public User(String accountId, String nickname, LoginType loginType, Theme theme){
//        this.accountId = accountId;
//        this.nickname = nickname;
//        this.loginType = loginType;
//        this.theme = theme;
//        this.role = RoleType.ROLE_USER;
//        this.isDeleted = false;
//    }
//
//    public User(SignUpRequestDto dto, Theme theme){
//        this.accountId = dto.getAccountId();
//        this.nickname = dto.getNickname();
//        this.loginType = LoginType.LOCAL;
//        this.theme = theme;
//        this.role = RoleType.ROLE_USER;
//        this.isDeleted = false;
//    }
}
