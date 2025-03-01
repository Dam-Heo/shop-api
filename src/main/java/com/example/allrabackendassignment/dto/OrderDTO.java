package com.example.allrabackendassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDTO {
    private Long customerId; // 고객 ID
    private List<OrderDetailDTO> orderDetails; // 주문 상세 정보
    private PaymentRequest paymentRequest; // 결제 요청 정보
}