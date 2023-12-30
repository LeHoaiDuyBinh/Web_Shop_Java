package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartCode(String cartCode);

    void deleteByCartCode(String cartCode);

    void deleteByProductCode(String productCode);
}
