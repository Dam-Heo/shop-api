package com.example.allrabackendassignment.repository;

import com.example.allrabackendassignment.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 사용자 ID로 고객을 조회하는 메서드
    Customer findByUserId(Long userId);
}
