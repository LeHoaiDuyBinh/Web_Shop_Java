package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.CartItem;
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

    public void saveOrderItem(String orderCode, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderCode(orderCode);
            orderItem.setProductCode(cartItem.getProductCode());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(String.valueOf(cartItem.getSize()));
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItemRepository.save(orderItem);
        }
    }
}
