package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, String> {
    OrderHistory findByEmail(String email);
    OrderHistory findByOrderCodeAndEmail(String orderCode, String email);
}
