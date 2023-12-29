package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.OrderHistoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryItemRepository extends JpaRepository<OrderHistoryItem, Long> {
    OrderHistoryItem findByOrderCode(String detailId);
}
