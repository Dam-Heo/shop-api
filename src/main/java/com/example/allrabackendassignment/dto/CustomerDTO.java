package com.example.allrabackendassignment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CustomerDTO {
    private Long id; // 고객 ID
    private Long userId; // 사용자 ID
    private String address; // 주소
    private String phoneNumber; // 전화번호
    private LocalDateTime createdAt; // 생성 시간
    private LocalDateTime updatedAt; // 수정 시간
}
