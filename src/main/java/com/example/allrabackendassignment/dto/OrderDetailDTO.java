package com.example.allrabackendassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailDTO {
    private Long productId; // 제품 ID
    private int quantity; // 수량
    private Long price; // 가격
}