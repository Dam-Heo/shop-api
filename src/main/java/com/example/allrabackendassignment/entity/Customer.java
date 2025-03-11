package com.example.allrabackendassignment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 고객 엔티티 클래스
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 고객 ID (고유 키)

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // 사용자와의 연관 관계

    // 고객의 추가 정보
    @Column(nullable = false)
    private String address; // 주소

    @Column(nullable = false)
    private String phoneNumber; // 전화번호

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 수정 시간

    @Builder
    public Customer(Long id, User user, String address, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

