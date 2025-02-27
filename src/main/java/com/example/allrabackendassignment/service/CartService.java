package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.CartDTO;
import com.example.allrabackendassignment.entity.Cart;
import com.example.allrabackendassignment.entity.Customer;
import com.example.allrabackendassignment.entity.Product;
import com.example.allrabackendassignment.repository.CartRepository;
import com.example.allrabackendassignment.repository.CustomerRepository;
import com.example.allrabackendassignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    /**
     * 특정 고객의 장바구니 항목을 조회합니다.
     * @param customerId 고객 ID
     * @return 장바구니 항목 목록
     */
    public List<Cart> getCartItems(Long customerId) {
        return cartRepository.findAllByCustomerId(customerId);
    }

    /**
     * 장바구니에 항목을 추가합니다.
     * @param cartDTO 장바구니 DTO
     * @return 추가된 장바구니 항목
     */
    public Cart addCartItem(CartDTO cartDTO) {
        Customer customer = customerRepository.getReferenceById(cartDTO.getCustomerId());
        Product product = productRepository.getReferenceById(cartDTO.getProductId());
        if(product.getStock() < cartDTO.getQuantity()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "재고가 부족합니다."
            );
        }
        Cart cart = Cart.builder()
                .customer(customer)
                .product(product)
                .quantity(cartDTO.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return cartRepository.save(cart);
    }

    /**
     * 특정 장바구니 항목을 삭제합니다.
     * @param cartId 장바구니 ID
     */
    public void deleteCartItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    /**
     * 특정 장바구니 항목의 수량을 업데이트합니다.
     * @param cartId 장바구니 ID
     * @param quantity 업데이트할 수량
     * @return 업데이트된 장바구니 항목
     */
    public Cart updateCartItem(Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        int sumQty = cart.getQuantity() + quantity;
        if(cart.getProduct().getStock() < sumQty) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "재고가 부족합니다."
            );
        }
        cart.setQuantity(quantity);
        cart.setUpdatedAt(LocalDateTime.now()); // 수정 시간 업데이트
        return cartRepository.save(cart);
    }
}
