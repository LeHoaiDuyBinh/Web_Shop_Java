package com.example.web_shop_ptit.admin.repository;

import com.example.web_shop_ptit.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUsernameAndPassword(String username, String password);

    Admin findByUsername(String username);
}
