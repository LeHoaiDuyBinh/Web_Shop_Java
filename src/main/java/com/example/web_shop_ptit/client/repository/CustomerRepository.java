package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByEmail(String email);
    boolean existsByEmail(String email);
}
