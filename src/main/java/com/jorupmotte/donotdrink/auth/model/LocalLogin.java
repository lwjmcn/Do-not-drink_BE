package com.jorupmotte.donotdrink.auth.model;

import com.jorupmotte.donotdrink.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "local_login")
@Getter
@Builder
@AllArgsConstructor
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
}