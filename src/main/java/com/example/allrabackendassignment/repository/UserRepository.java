package com.example.allrabackendassignment.repository;

import com.example.allrabackendassignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름으로 사용자를 조회하는 메서드
    User findByUsername(String username);
}
