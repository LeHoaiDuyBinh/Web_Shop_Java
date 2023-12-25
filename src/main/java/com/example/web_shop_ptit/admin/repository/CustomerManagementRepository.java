package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.CustomerManagement;
import com.example.web_shop_ptit.client.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerManagementRepository extends JpaRepository<CustomerManagement, String> {
    CustomerManagement findByEmail(String email);
}
