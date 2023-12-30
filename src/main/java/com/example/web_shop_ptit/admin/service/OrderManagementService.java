package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.OrderManagement;
import com.example.web_shop_ptit.admin.repository.OrderManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManagementService {
    @Autowired
    private OrderManagementRepository repo;

    public List<OrderManagement> listAll() {
        return (List<OrderManagement>) repo.findAll();
    }
    public List<OrderManagement> listOrderNotDelivered(String MaSanPham) {
        return (List<OrderManagement>) repo.findOrdersByStatusAndProductCode(MaSanPham);
    }

    public OrderManagement findOrderManagementByOrderCode(String MaSanPham) {
        return repo.findOrderManagementByOrderCode(MaSanPham);
    }

    public void updateOrderState(String MaDonHang, String action) {

        OrderManagement order = repo.findOrderManagementByOrderCode(MaDonHang);

        if (order != null){
            order.setState(action);
            repo.save(order);
        }
    }
}
