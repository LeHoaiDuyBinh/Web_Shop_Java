package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Order;
import com.example.web_shop_ptit.client.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
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

    public Order findByOrderCodeAndEmail(String orderCode, String email) {return orderRepository.findByOrderCodeAndEmail(orderCode, email);}

    public void saveOrder(String orderCode, LocalDateTime orderDate, Long totalPrice, String email, String address) {
        Order order = new Order();
        order.setOrderCode(orderCode);
        order.setOrderDate(orderDate);
        order.setState("pending");
        order.setTotalPrice(totalPrice);
        order.setEmail(email);
        order.setAddress(address);
        orderRepository.save(order);
    }
}
