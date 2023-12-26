package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Customer;
import com.example.web_shop_ptit.client.repository.CustomerRepository;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    @Autowired
    private CustomerRepository customerRepository;

    public void saveRegistrationInfo(String fullname, String email, String password, String phone) {
        Customer customer = new Customer();
        customer.setFullName(fullname);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setPhoneNumber(phone);

        customerRepository.save(customer);
    }

    public void updatePassword(String email, String newPassword) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            // Hash the new password using BCryptPasswordEncoder
            String hashedPassword = newPassword;
            customer.setPassword(hashedPassword);
            customerRepository.save(customer);


        }
    }

}
