package com.example.allrabackendassignment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentRequest {
    private Long customerId; // 고객 ID
    private Long orderId; // 주문 ID
    private Long amount; // 결제 금액
    private String paymentMethod; // 결제 방법 (예: 카드, 페이팔 등)
}

