package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.PaymentRequest;
import com.example.allrabackendassignment.dto.PaymentResponse;
import com.example.allrabackendassignment.entity.Cart;
import com.example.allrabackendassignment.entity.Order;
import com.example.allrabackendassignment.entity.OrderDetail;
import com.example.allrabackendassignment.repository.CartRepository;
import com.example.allrabackendassignment.repository.OrderDetailRepository;
import com.example.allrabackendassignment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;
    private final PaymentService paymentService;

    /**
     * 특정 고객의 주문 내역을 조회합니다.
     *
     * @param customerId 고객 ID
     * @return 주문 목록
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    /**
     * 주문을 생성하고 결제를 처리합니다.
     * @param order 주문 정보
     * @param paymentRequest 결제 요청 정보
     * @return 생성된 주문
     */
    @Transactional
    public Order placeOrder(Order order, PaymentRequest paymentRequest) {
        // 재고 확인 및 업데이트
        order.getOrderDetails().forEach(orderDetail ->
                productService.updateStock(orderDetail.getProduct().getId(), orderDetail.getQuantity()));

        // 주문 저장
        orderRepository.save(order);

        // 결제 요청
        paymentRequest.setOrderId(order.getId());
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        if ("SUCCESS".equals(paymentResponse.getStatus())) {
            order.setStatus("완료");
        } else {
            order.setStatus("실패");
        }

        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    /**
     * 장바구니에 담긴 모든 상품을 하나의 주문으로 변환하고 결제를 처리합니다.
     * @param customerId 고객 ID
     * @param paymentRequest 결제 요청 정보
     * @return 생성된 주문
     */
    @Transactional
    public Order placeOrdersFromCart(Long customerId, PaymentRequest paymentRequest) {
        List<Cart> cartItems = cartRepository.findAllByCustomerId(customerId);
        // 각 장바구니 항목에 대해 재고 확인 및 업데이트
        cartItems.forEach(cart -> productService.updateStock(cart.getProduct().getId(), cart.getQuantity()));

        // 전체 주문 가격 계산
        long totalPrice = cartItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        Order order = Order.builder()
                .customer(cartItems.get(0).getCustomer())
                .totalPrice(totalPrice)
                .status("처리 중")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 주문 저장
        orderRepository.save(order);

        // 주문 상세 항목 저장
        List<OrderDetail> orderDetails = cartItems.stream().map(cart -> OrderDetail.builder()
                        .order(order)
                        .product(cart.getProduct())
                        .quantity(cart.getQuantity())
                        .price(cart.getProduct().getPrice())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        orderDetailRepository.saveAll(orderDetails);

        // 주문에 주문 상세 항목 설정
        order.setOrderDetails(orderDetails);

        // 장바구니 비우기
        cartRepository.deleteAll(cartItems);

        // 결제 요청
        paymentRequest.setCustomerId(order.getCustomer().getId());
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setAmount(order.getTotalPrice());
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        if ("SUCCESS".equals(paymentResponse.getStatus())) {
            order.setStatus("완료");
        } else {
            order.setStatus("실패");
        }

        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
}

