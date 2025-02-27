package com.example.allrabackendassignment.repository;

import com.example.allrabackendassignment.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByCustomerId(Long customerId);
}
