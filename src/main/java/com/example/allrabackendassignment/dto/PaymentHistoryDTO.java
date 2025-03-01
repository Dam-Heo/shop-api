package com.example.allrabackendassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentHistoryDTO {
    private Long id; // 결제 이력 ID
    private Long customerId; // 고객 ID
    private Long orderId; // 주문 ID
    private Long amount; // 결제 금액
    private String paymentMethod; // 결제 방법
    private String status; // 결제 상태
    private String transactionId; // 트랜잭션 ID
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간
}
