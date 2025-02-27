package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.entity.PaymentHistory;
import com.example.allrabackendassignment.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-history")
@RequiredArgsConstructor
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    /**
     * 특정 고객의 결제 이력을 조회합니다.
     * @param customerId 고객 ID
     * @return 결제 이력 목록
     */
    @GetMapping("/{customerId}")
    public List<PaymentHistory> getPaymentHistoriesByCustomerId(@PathVariable Long customerId) {
        return paymentHistoryService.getPaymentHistoriesByCustomerId(customerId);
    }
}

