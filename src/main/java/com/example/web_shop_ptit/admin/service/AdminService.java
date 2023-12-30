package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.Admin;
import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    public List<Admin> listAll() {
        return (List<Admin>) repo.findAll();
    }
    public Admin validateLogin(String username, String password) {
        String passHash = hashPassword(password);
        Admin admin = repo.findByUsernameAndPassword(username, passHash);
        if(admin != null) {
            return admin;
        }
        return null;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return new String(Hex.encode(encodedHash));
        } catch (NoSuchAlgorithmException e) {
            // Xử lý exception nếu cần
            e.printStackTrace();
            return null;
        }
    }
}
