package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.CartItem;
import com.example.web_shop_ptit.client.entity.Product;
import com.example.web_shop_ptit.client.entity.Size;
import com.example.web_shop_ptit.client.repository.CartItemRepository;
import jakarta.transaction.Transactional;
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

    public void addToCart(String cartCode,String productCode, int quantity, Size size, Long totalPrice) {
        CartItem cartItem= new CartItem();
        cartItem.setCartCode(cartCode);
        System.out.println("prouctcode: " +productCode);
        cartItem.setProductCode(productCode);
        cartItem.setQuantity(quantity);
        cartItem.setSize(size);
        cartItem.setTotalPrice(totalPrice/1000);
        cartItemRepository.save(cartItem);
    }

    public void updateQuantityAndPrice(List<CartItem> cartItemList, String[] quantity,
                                       String[] totalPrice){
        for(int i = 0; i < cartItemList.size(); i++){
            cartItemList.get(i).setQuantity(Integer.parseInt(quantity[i]));
            cartItemList.get(i).setTotalPrice(Long.valueOf(totalPrice[i]));
            cartItemRepository.save(cartItemList.get(i));
        }

    }

    @Transactional
    public void deleteCartItem(String cartCode){
        cartItemRepository.deleteByCartCode(cartCode);
    }

    @Transactional
    public void deleteCartProduct(String productCode){
        cartItemRepository.deleteByProductCode(productCode);
    }
}
