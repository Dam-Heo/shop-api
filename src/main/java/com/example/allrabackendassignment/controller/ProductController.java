package com.example.allrabackendassignment.controller;

import com.example.allrabackendassignment.entity.Product;
import com.example.allrabackendassignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * 모든 상품을 조회합니다.
     * @return 상품 목록
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * 재고수량이 0보다 큰 상품을 조회합니다.
     * @return 상품 목록
     */
    @GetMapping("/available")
    public List<Product> getAvailableProducts() {
        return productService.getAvailableProducts();
    }
}
