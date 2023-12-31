package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.ShoppingCart;
import com.example.web_shop_ptit.client.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public void saveShoppingCart(String cardCode, String email) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartCode(cardCode);
        shoppingCart.setEmail(email);
        shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart findByCartCode(String cartCode) {
        return shoppingCartRepository.findByCartCode(cartCode);
    }

    public ShoppingCart findByEmail(String email) {
        return shoppingCartRepository.findByEmail(email);
    }
}
