package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.OrderItem;
import com.example.web_shop_ptit.client.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> listAll(){
        return (List<OrderItem>) orderItemRepository.findAll();
    }

    public OrderItem findByOrderCode(String orderCode) {
        return orderItemRepository.findByOrderCode(orderCode);
    }
}
