package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.OrdersHistoryItemManagement;
import com.example.web_shop_ptit.admin.repository.OrdersHistoryItemsManagementRepository;
import com.example.web_shop_ptit.client.entity.OrderHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrdersHistoryItemsManagementService {

    @Autowired
    private OrdersHistoryItemsManagementRepository repo;

    public List<OrdersHistoryItemManagement> findByOrderCode(String orderCode) {
        return repo.findByOrderCode(orderCode);
    }
    public List<OrdersHistoryItemManagement> listAll(){
        return (List<OrdersHistoryItemManagement>) repo.findAll();
    }
    public List<OrdersHistoryItemManagement> orderDetails(String orderCode) {
        return repo.findByOrderCode(orderCode);
    }
}

