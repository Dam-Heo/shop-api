package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.entity.PaymentHistory;
import com.example.allrabackendassignment.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;

    /**
     * 특정 고객의 결제 이력 조회
     * @param customerId 고객 ID
     * @return 결제 이력 목록
     */
    public List<PaymentHistory> getPaymentHistoryByCustomerId(Long customerId) {
        return paymentHistoryRepository.findAllByCustomerId(customerId);
    }

    /**
     * 모든 결제 이력 조회
     * @return 결제 이력 목록
     */
    public List<PaymentHistory> getAllPaymentHistories() {
        return paymentHistoryRepository.findAll();
    }
}

