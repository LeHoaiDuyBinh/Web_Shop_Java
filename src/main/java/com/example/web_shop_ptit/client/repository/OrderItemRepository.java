package com.example.web_shop_ptit.client.repository;


import com.example.web_shop_ptit.client.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByOrderCode(String orderCode);
}
