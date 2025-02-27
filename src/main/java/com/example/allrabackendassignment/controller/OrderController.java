package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.dto.PaymentRequest;
import com.example.allrabackendassignment.entity.Order;
import com.example.allrabackendassignment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 특정 고객의 주문 내역을 조회합니다.
     * @param customerId 고객 ID
     * @return 주문 목록
     */
    @GetMapping("/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }

    /**
     * 주문을 생성하고 결제를 처리합니다.
     * @param order 주문 정보
     * @param paymentRequest 결제 요청 정보
     * @return 생성된 주문
     */
    @PostMapping
    public Order placeOrder(@RequestBody Order order, @RequestBody PaymentRequest paymentRequest) {
        return orderService.placeOrder(order, paymentRequest);
    }

    /**
     * 장바구니에 담긴 모든 상품을 하나의 주문으로 변환하고 결제를 처리합니다.
     * @param customerId 고객 ID
     * @param paymentRequest 결제 요청 정보
     * @return 생성된 주문
     */
    @PostMapping("/{customerId}/cart-to-order")
    public Order placeOrdersFromCart(@PathVariable Long customerId, @RequestBody PaymentRequest paymentRequest) { //PaymentRequest 에는 paymentMethod(결제방법)만 보내면 됨
        return orderService.placeOrdersFromCart(customerId, paymentRequest);
    }
}

