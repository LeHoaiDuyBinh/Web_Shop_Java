package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.OrderManagement;
import com.example.web_shop_ptit.admin.entity.PaymentManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentManagementRepository extends JpaRepository<PaymentManagement, String> {
}
