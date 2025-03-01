package com.example.allrabackendassignment.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 결제 요청 이력 엔티티 클래스
 */
@Entity
@Table(name = "payment_history")
@Getter
@Setter
@NoArgsConstructor
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // 결제 요청 이력 ID (고유 키)

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_id", nullable = false)
    private Long orderId; // 주문 ID

    @Column(name = "amount", nullable = false)
    private Long amount; // 결제 금액

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // 결제 방법

    @Column(name = "status", nullable = false)
    private String status; // 결제 상태

    @Column(name = "transaction_id")
    private String transactionId; // 트랜잭션 ID

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 수정 시간
}
