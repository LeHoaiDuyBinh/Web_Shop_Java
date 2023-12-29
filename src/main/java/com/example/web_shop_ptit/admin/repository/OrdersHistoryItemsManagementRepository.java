package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.OrdersHistoryItemManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersHistoryItemsManagementRepository extends JpaRepository<OrdersHistoryItemManagement, Long> {
    List<OrdersHistoryItemManagement> findByOrderCode(String orderCode);
}

