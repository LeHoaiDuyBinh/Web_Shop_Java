package com.example.web_shop_ptit.client.repository;


import com.example.web_shop_ptit.client.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByEmail(String email);
}
