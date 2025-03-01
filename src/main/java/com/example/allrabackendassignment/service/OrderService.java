package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.OrderDTO;
import com.example.allrabackendassignment.dto.OrderDetailDTO;
import com.example.allrabackendassignment.dto.PaymentRequest;
import com.example.allrabackendassignment.dto.PaymentResponse;
import com.example.allrabackendassignment.entity.*;
import com.example.allrabackendassignment.repository.CartRepository;
import com.example.allrabackendassignment.repository.OrderDetailRepository;
import com.example.allrabackendassignment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
     * @param customerId 고객 ID
     * @return 주문 목록
     */
    public List<Order> getOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if (orders.isEmpty()) {
            throw new NoSuchElementException("해당 ID를 가진 고객의 주문을 찾을 수 없습니다: " + customerId);
        }
        return orders;
    }

    /**
     * 주문을 생성하고 결제를 처리합니다.
     * @param orderDTO 주문 정보
     * @return 생성된 주문
     */
    @Transactional
    public Order placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomer(Customer.builder().id(orderDTO.getCustomerId()).build());
        order.setOrderDetails(new ArrayList<>());

        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProduct(Product.builder().id(orderDetailDTO.getProductId()).build());
            orderDetail.setQuantity(orderDetailDTO.getQuantity());
            orderDetail.setPrice(orderDetailDTO.getPrice());
            order.getOrderDetails().add(orderDetail);
        }

        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus("처리 중");

        // 주문 저장
        orderRepository.save(order);

        // 결제 요청
        PaymentRequest paymentRequest = orderDTO.getPaymentRequest();
        paymentRequest.setOrderId(order.getId());
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        if ("SUCCESS".equals(paymentResponse.getStatus())) {
            // 결제가 성공한 경우에만 재고 업데이트
            for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetails()) {
                productService.updateStock(orderDetailDTO.getProductId(), orderDetailDTO.getQuantity());
            }
            order.setStatus("완료");
        } else {
            order.setStatus("실패");
        }

        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Transactional
    public Order placeOrdersFromCart(Long customerId, PaymentRequest paymentRequest) {
        List<Cart> cartItems = cartRepository.findByCustomerId(customerId);

        // 전체 주문 가격 계산
        long totalPrice = cartItems.stream()
                .mapToLong(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();

        Order order = Order.builder()
                .customer(cartItems.getFirst().getCustomer())
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

        // 결제 요청
        paymentRequest.setCustomerId(order.getCustomer().getId());
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setAmount(order.getTotalPrice());
        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        if ("SUCCESS".equals(paymentResponse.getStatus())) {
            // 결제가 성공한 경우에만 재고 업데이트
            cartItems.forEach(cart -> productService.updateStock(cart.getProduct().getId(), cart.getQuantity()));
            order.setStatus("완료");
            // 장바구니 비우기
            cartRepository.deleteAll(cartItems);
        } else {
            order.setStatus("실패");
        }

        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }
}

