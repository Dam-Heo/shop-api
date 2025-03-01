package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.PaymentRequest;
import com.example.allrabackendassignment.dto.PaymentResponse;
import com.example.allrabackendassignment.entity.PaymentHistory;
import com.example.allrabackendassignment.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private static final String PAYMENT_API_URL = "https://allra-backend-assignment.free.beeceptor.com/api/v1/payment";
    /**
     * 외부 결제 API를 호출하여 결제를 처리합니다.
     * @param paymentRequest 결제 요청 정보
     * @return 결제 응답 정보
     */
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<PaymentRequest> requestEntity = new HttpEntity<>(paymentRequest, headers);

        ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(
                PAYMENT_API_URL,
                HttpMethod.POST,
                requestEntity,
                PaymentResponse.class
        );

        PaymentResponse paymentResponse;
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            if(Objects.requireNonNull(responseEntity.getBody()).getStatus().equals("SUCCESS")) {
                paymentResponse = responseEntity.getBody();
                savePaymentHistory(paymentRequest, Objects.requireNonNull(paymentResponse));
            }else{
                paymentResponse = responseEntity.getBody();
                Objects.requireNonNull(paymentResponse).setStatus("FAILURE");
                savePaymentHistory(paymentRequest, paymentResponse);
            }
        } else {
            paymentResponse = responseEntity.getBody();
            Objects.requireNonNull(paymentResponse).setStatus("FAILURE");
            savePaymentHistory(paymentRequest, paymentResponse);
        }

        return paymentResponse;
    }

    /**
     * 결제 요청 이력을 저장합니다.
     * @param paymentRequest 결제 요청 정보
     * @param paymentResponse 결제 응답 정보
     */
    private void savePaymentHistory(PaymentRequest paymentRequest, PaymentResponse paymentResponse) {
        PaymentHistory paymentHistory = PaymentHistory.builder()
                .customerId(paymentRequest.getCustomerId())
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .status(paymentResponse.getStatus())
                .transactionId(paymentResponse.getTransactionId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        paymentHistoryRepository.save(paymentHistory);
    }
}

