package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.OrdersHistoryManagement;
import com.example.web_shop_ptit.admin.repository.OrdersHistoryManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrdersHistoryManagementService {
    @Autowired
    private OrdersHistoryManagementRepository repo;

    public List<OrdersHistoryManagement> listAll() {
        return (List<OrdersHistoryManagement>) repo.findAll();
    }
    public OrdersHistoryManagement getOrdersHistoryByOrderCode(String orderCode) {
        List<OrdersHistoryManagement> resultList = repo.findByOrderCode(orderCode);
        return resultList.isEmpty() ? null : resultList.get(0);
    }
}
