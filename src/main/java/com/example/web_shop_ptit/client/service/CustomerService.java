package com.example.web_shop_ptit.client.service;


import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Customer;
import com.example.web_shop_ptit.client.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private static CustomerRepository repo;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public static List<Customer> listAll() {
        return (List<Customer>) repo.findAll();
    }

    public Customer findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repo = customerRepository;
        this.bCryptPasswordEncoder = passwordEncoder;
        System.out.println(this.bCryptPasswordEncoder);
    }

    // Kiểm tra đăng nhập
    public static boolean checkLogin(String email, String password) {
        Customer customer = repo.findByEmail(email);

        if (customer != null) {
            // Băm mật khẩu nhập vào và so sánh với mật khẩu đã lưu trong cơ sở dữ liệu
            String hashedPasswordInput = hashPassword(password);

            return hashedPasswordInput.equals(customer.getPassword());
        }

        return false;
    }

    public void updatePassword(String email, String newPassword) {
        Customer customer = repo.findByEmail(email);
        if (customer != null) {
            // Hash the new password using BCryptPasswordEncoder
            String hashedPassword = newPassword;
            customer.setPassword(hashPassword(hashedPassword));
            repo.save(customer);
        }
    }

    public void updateInfomation(String email, String fullName, String phoneNumber) {
        Customer customer = repo.findByEmail(email);
        if (customer != null) {
            // Hash the new password using BCryptPasswordEncoder
            customer.setFullName(fullName);
            customer.setPhoneNumber(phoneNumber);
            repo.save(customer);
        }
    }

    // Hàm băm mật khẩu sử dụng SHA-256
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
    public boolean isEmailExists(String email) {
        return repo.existsByEmail(email);
    }

}
