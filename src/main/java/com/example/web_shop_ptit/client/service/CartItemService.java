package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.CartItem;
import com.example.web_shop_ptit.client.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findByCartItem(String cartCode) {
        try {
            List<CartItem> cartItems = cartItemRepository.findByCartCode(cartCode);
            System.out.println("Cart items size: " + cartItems.size());
            return cartItems;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
