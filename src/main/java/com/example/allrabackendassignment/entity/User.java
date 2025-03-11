package com.example.allrabackendassignment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 ID

    @Column(nullable = false)
    private String username; // 사용자 이름

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String role; // 역할 (예: ROLE_USER)

    @Column(nullable = false, unique = true)
    private String email; // 이메일

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 수정 시간

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Customer customer; // 고객 정보 (연관 관계)

    @Builder
    public User(Long id, String username, String password, String role, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
