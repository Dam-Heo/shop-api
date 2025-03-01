package com.example.allrabackendassignment.service;

import com.example.allrabackendassignment.entity.Product;
import com.example.allrabackendassignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 모든 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 특정 상품 조회
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    // 재고 수량이 0보다 큰 상품 조회
    public List<Product> getProductsInStock() {
        return productRepository.findByStockGreaterThan(0);
    }

    // 상품 추가
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // 상품 수정
    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        return productRepository.save(existingProduct);
    }

    // 상품 삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
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

    /**
     * 제품의 재고를 확인하고, 필요한 경우 업데이트합니다.
     * @param productId 제품 ID
     * @param quantity 필요한 수량
     * @return 재고가 충분한지 여부
     */
    @Transactional
    public boolean checkAndUpdateStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        if (product.getStock() < quantity) {
            return false; // 재고 부족
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        return true; // 재고 충분
    }
}

