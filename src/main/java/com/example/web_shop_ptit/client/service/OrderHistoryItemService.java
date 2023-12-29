package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.OrderHistoryItem;
import com.example.web_shop_ptit.client.repository.OrderHistoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryItemService {
    @Autowired
    private OrderHistoryItemRepository orderHistoryItemRepository;

    public List<OrderHistoryItem> listAll(){
        return (List<OrderHistoryItem>) orderHistoryItemRepository.findAll();
    }

    public OrderHistoryItem findByOrderCode(String orderCode) {
        return orderHistoryItemRepository.findByOrderCode(orderCode);
    }
}
