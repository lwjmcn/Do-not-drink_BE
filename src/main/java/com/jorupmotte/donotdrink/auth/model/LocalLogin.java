package com.jorupmotte.donotdrink.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "local_login")
@Getter
@NoArgsConstructor
public class LocalLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 45)
    @NotNull
    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Size(max = 60)
    @NotNull
    @Column(name = "password", nullable = false, length = 60)
    private String password;

    public LocalLogin(User user, String email, String encodedPassword){
        this.user = user;
        this.email = email;
        this.password = encodedPassword;
    }
}