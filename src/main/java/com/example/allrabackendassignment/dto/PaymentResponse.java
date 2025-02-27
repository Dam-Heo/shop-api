package com.example.allrabackendassignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
    private String status; // 결제 상태 (예: 성공, 실패)
    private String message; // 결제 응답 메시지
    private String transactionId; // 트랜잭션 ID
}
