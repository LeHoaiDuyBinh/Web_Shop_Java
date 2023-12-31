package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.OrdersHistoryItemManagement;
import com.example.web_shop_ptit.admin.entity.OrdersHistoryManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersHistoryManagementRepository extends JpaRepository<OrdersHistoryManagement, String> {
    List<OrdersHistoryManagement> findByOrderCode(String orderCode);
}



