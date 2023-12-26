package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Order;
import com.example.web_shop_ptit.client.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> listAll(){
        return (List<Order>) orderRepository.findAll();
    }

    public Order findByEmail(String email){
        return orderRepository.findByEmail(email);
    }
}
