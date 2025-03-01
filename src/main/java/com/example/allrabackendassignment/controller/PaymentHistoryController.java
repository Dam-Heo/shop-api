package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.entity.PaymentHistory;
import com.example.allrabackendassignment.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-history")
@RequiredArgsConstructor
public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    /**
     * 특정 고객의 결제 이력 조회
     * @param customerId 고객 ID
     * @return 결제 이력 목록
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<List<PaymentHistory>> getPaymentHistoryByCustomerId(@PathVariable Long customerId) {
        List<PaymentHistory> paymentHistoryList = paymentHistoryService.getPaymentHistoryByCustomerId(customerId);
        return new ResponseEntity<>(paymentHistoryList, HttpStatus.OK);
    }

    /**
     * 모든 결제 이력 조회
     * @return 결제 이력 목록
     */
    @GetMapping
    public ResponseEntity<List<PaymentHistory>> getAllPaymentHistories() {
        List<PaymentHistory> paymentHistoryList = paymentHistoryService.getAllPaymentHistories();
        return new ResponseEntity<>(paymentHistoryList, HttpStatus.OK);
    }
}

