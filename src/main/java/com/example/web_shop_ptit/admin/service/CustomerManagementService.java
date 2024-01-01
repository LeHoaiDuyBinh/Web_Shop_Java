package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.CustomerManagement;
import com.example.web_shop_ptit.admin.repository.CustomerManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerManagementService {
    @Autowired
    private CustomerManagementRepository repo;

    public List<CustomerManagement> listAll() {
        return (List<CustomerManagement>) repo.findAll();
    }

    public CustomerManagement getCustomerByEmail(String email) {

        return repo.findByEmail(email);
    }

    public void saveCustomer(String email, String fullname, String phone) {
        CustomerManagement customers = new CustomerManagement();
        customers.setEmail(email);
        customers.setFullName(fullname);
        customers.setPhone(phone);
        repo.save(customers);
    }

    public void updateCustomer(String email, String fullname, String phone) {
        CustomerManagement customer = repo.findByEmail(email);
        if (customer != null) {
            customer.setFullName(fullname);
            customer.setPhone(phone);
            repo.save(customer);
        }
    }
}