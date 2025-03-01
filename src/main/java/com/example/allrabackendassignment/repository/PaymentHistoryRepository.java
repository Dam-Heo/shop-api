package com.example.allrabackendassignment.repository;

import com.example.allrabackendassignment.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 결제 요청 이력 레퍼지토리 인터페이스
 */
@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
    List<PaymentHistory> findAllByCustomerId(Long customerId);
}

