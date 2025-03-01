package com.example.allrabackendassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id; // 제품 ID
    private String name; // 제품 이름
    private String description; // 제품 설명
    private Long price; // 제품 가격
    private int stock; // 재고 수량
}
