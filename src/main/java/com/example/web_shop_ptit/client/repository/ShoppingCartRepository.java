package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    ShoppingCart findByCartCode(String cardCode);
}
