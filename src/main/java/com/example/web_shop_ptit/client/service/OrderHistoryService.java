package com.example.web_shop_ptit.client.service;


import com.example.web_shop_ptit.client.entity.OrderHistory;
import com.example.web_shop_ptit.client.repository.OrderHistoryRepository;
import com.example.web_shop_ptit.client.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryService {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public List<OrderHistory> listAll(){
        return (List<OrderHistory>) orderHistoryRepository.findAll();
    }

    public OrderHistory findByEmail(String email){
        return orderHistoryRepository.findByEmail(email);
    }
}
