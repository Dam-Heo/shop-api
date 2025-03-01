package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.dto.OrderDTO;
import com.example.allrabackendassignment.dto.PaymentRequest;
import com.example.allrabackendassignment.entity.Order;
import com.example.allrabackendassignment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * 주문을 생성하고 결제를 처리합니다.
     * @param orderDTO 주문 정보
     * @return 생성된 주문
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderDTO orderDTO) {
        Order createdOrder = orderService.placeOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * 장바구니에 담긴 모든 상품을 하나의 주문으로 변환하고 결제를 처리합니다.
     * @param customerId 고객 ID
     * @param paymentRequest 결제 요청 정보 (결제 방법만 포함)
     * @return 생성된 주문
     */
    @PostMapping("/{customerId}/cart-to-order")
    public ResponseEntity<Order> placeOrdersFromCart(@PathVariable Long customerId, @RequestBody PaymentRequest paymentRequest) {
        Order createdOrder = orderService.placeOrdersFromCart(customerId, paymentRequest);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}

