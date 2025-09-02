package com.example.quiz.model.entity;

import com.example.quiz.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    private Instant dob;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    private String bio;

    private String location;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    private String otp;
    private Instant otpGeneratedTime;
    private boolean isActive;
}
