package com.example.allrabackendassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartDTO {
    private Long productId;
    private Integer quantity;
    private Long customerId;

}
