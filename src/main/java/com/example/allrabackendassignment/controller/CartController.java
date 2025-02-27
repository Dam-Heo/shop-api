package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.dto.CartDTO;
import com.example.allrabackendassignment.entity.Cart;
import com.example.allrabackendassignment.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    /**
     * 특정 고객의 장바구니 항목을 조회합니다.
     * @param customerId 고객 ID
     * @return 장바구니 항목 목록
     */
    @GetMapping("/{customerId}")
    public List<Cart> getCartItems(@PathVariable Long customerId) {
        return cartService.getCartItems(customerId);
    }

    /**
     * 장바구니에 항목을 추가합니다.
     * @param params 장바구니 항목
     * @return 추가된 장바구니 항목
     */
    @PostMapping
    public Cart addCartItem(@RequestBody CartDTO params) {
        return cartService.addCartItem(params);
    }

    /**
     * 특정 장바구니 항목을 삭제합니다.
     * @param cartId 장바구니 ID
     */
    @DeleteMapping("/{cartId}")
    public void deleteCartItem(@PathVariable Long cartId) {
        cartService.deleteCartItem(cartId);
    }

    /**
     * 특정 장바구니 항목의 수량을 업데이트합니다.
     * @param cartId 장바구니 ID
     * @param quantity 업데이트할 수량
     * @return 업데이트된 장바구니 항목
     */
    @PatchMapping("/{cartId}")
    public Cart updateCartItem(@PathVariable Long cartId, @RequestParam Integer quantity) {
        return cartService.updateCartItem(cartId, quantity);
    }
}

