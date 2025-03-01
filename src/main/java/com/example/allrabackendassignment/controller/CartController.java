package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.dto.CartDTO;
import com.example.allrabackendassignment.entity.Cart;
import com.example.allrabackendassignment.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 아이템 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<List<Cart>> getCartItems(@PathVariable Long customerId) {
        List<Cart> cartItems = cartService.getCartItemsByCustomerId(customerId);
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    // 장바구니 아이템 추가
    @PostMapping
    public ResponseEntity<String> addCartItem(@RequestBody CartDTO cartDTO) {
        boolean stockAvailable = cartService.addCartItem(cartDTO);
        if (!stockAvailable) {
            return new ResponseEntity<>("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("장바구니에 아이템이 추가되었습니다.", HttpStatus.CREATED);
    }

    // 장바구니 아이템 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCartItem(@PathVariable Long id, @RequestBody CartDTO cartDTO) {
        boolean stockAvailable = cartService.updateCartItem(id, cartDTO);
        if (!stockAvailable) {
            return new ResponseEntity<>("재고가 부족합니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("장바구니 아이템이 수정되었습니다.", HttpStatus.OK);
    }

    // 장바구니 아이템 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

