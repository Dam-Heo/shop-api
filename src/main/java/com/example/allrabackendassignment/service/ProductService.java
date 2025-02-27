package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.entity.Product;
import com.example.allrabackendassignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 모든 상품을 조회합니다.
     *
     * @return 상품 목록
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 재고 수량이 0보다 큰 상품을 조회합니다.
     *
     * @return 재고 수량이 0보다 큰 상품 목록
     */
    public List<Product> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    /**
     * 특정 상품의 재고를 확인합니다.
     *
     * @param productId 상품 ID
     * @return 재고 수량
     */
    public int getStock(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."))
                .getStock();
    }

    /**
     * 특정 상품의 재고를 업데이트합니다.
     *
     * @param productId 상품 ID
     * @param quantity  감소시킬 수량
     */
    public void updateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new RuntimeException("재고가 부족합니다.");
        }
        productRepository.updateStock(newStock, productId);
    }
}

