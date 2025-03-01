package com.example.allrabackendassignment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 상품 엔티티 클래스
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 상품 ID (고유 키)

    @Column(name = "name", nullable = false)
    private String name; // 상품 이름

    @Column(name = "description", length = 1000)
    private String description; // 상품 설명

    @Column(name = "price", nullable = false)
    private Long price; // 상품 가격

    @Column(name = "stock", nullable = false)
    private Integer stock; // 재고 수량

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 수정 시간
}

