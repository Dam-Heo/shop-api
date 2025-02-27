package com.example.allrabackendassignment.repository;

import com.example.allrabackendassignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStockGreaterThan(int i);
    /**
     * 재고 수량을 업데이트합니다.
     * @param stock 재고 수량
     * @param id 상품 ID
     * @return 업데이트된 레코드 수
     */
    @Modifying
    @Query("UPDATE Product p SET p.stock = :stock WHERE p.id = :id")
    int updateStock(@Param("stock") int stock, @Param("id") Long id);
}
