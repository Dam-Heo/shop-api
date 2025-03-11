package com.example.allrabackendassignment.dto;

import com.example.allrabackendassignment.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private User user; // 사용자 정보
    private CustomerDTO customer; // 고객 정보 (DTO)
}
