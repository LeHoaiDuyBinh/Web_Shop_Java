package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.OrderManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderManagementRepository extends JpaRepository<OrderManagement, String> {
    @Query("SELECT o FROM OrderManagement o " +
            "JOIN o.orderItemList oi " +
            "WHERE (o.state = 'pending' OR o.state = 'delivering') " +
            "AND oi.product.productCode = :productCode")
    List<OrderManagement> findOrdersByStatusAndProductCode(@Param("productCode") String productCode);

    OrderManagement findOrderManagementByOrderCode(String productCode);
}
