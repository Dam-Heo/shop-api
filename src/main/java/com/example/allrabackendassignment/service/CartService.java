package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.dto.CartDTO;
import com.example.allrabackendassignment.entity.Cart;
import com.example.allrabackendassignment.entity.Customer;
import com.example.allrabackendassignment.entity.Product;
import com.example.allrabackendassignment.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;


    // 특정 고객의 장바구니 아이템 조회
    public List<Cart> getCartItemsByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    // 장바구니 아이템 추가
    @Transactional
    public boolean addCartItem(CartDTO cartDTO) {
        boolean stockAvailable = productService.checkAndUpdateStock(cartDTO.getProductId(), cartDTO.getQuantity());
        if (!stockAvailable) {
            return false; // 재고 부족
        }

        Cart cart = Cart.builder()
                .customer(Customer.builder().id(cartDTO.getCustomerId()).build())
                .product(Product.builder().id(cartDTO.getProductId()).build())
                .quantity(cartDTO.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cartRepository.save(cart);
        return true; // 재고 충분
    }

    // 장바구니 아이템 수정
    @Transactional
    public boolean updateCartItem(Long id, CartDTO cartDTO) {
        Cart existingCart = cartRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cart item not found"));

        int quantityDifference = cartDTO.getQuantity() - existingCart.getQuantity();
        boolean stockAvailable = productService.checkAndUpdateStock(existingCart.getProduct().getId(), quantityDifference);
        if (!stockAvailable) {
            return false; // 재고 부족
        }

        existingCart.setQuantity(cartDTO.getQuantity());
        existingCart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(existingCart);
        return true; // 재고 충분
    }

    // 장바구니 아이템 삭제
    public void deleteCartItem(Long id) {
        cartRepository.deleteById(id);
    }
}
