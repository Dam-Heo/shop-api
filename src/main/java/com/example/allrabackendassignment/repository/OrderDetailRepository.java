package com.example.allrabackendassignment.repository;

import com.example.allrabackendassignment.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
