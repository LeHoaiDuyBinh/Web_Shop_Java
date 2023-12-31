package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.OrderManagement;
import com.example.web_shop_ptit.admin.entity.PaymentManagement;
import com.example.web_shop_ptit.admin.exception.SavePaymentException;
import com.example.web_shop_ptit.admin.repository.PaymentManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentManagementService {
    @Autowired
    private PaymentManagementRepository repo;

    public void savePayment(OrderManagement order, String paymentCode) {
        try {
            PaymentManagement payment = new PaymentManagement();
            payment.setPaymentCode(paymentCode);
            payment.setOrder(order);
            payment.setType("cash");
            payment.setPaymentDate(new Date());
            repo.save(payment);
        }
        catch (Exception e){
            throw new SavePaymentException("Lỗi khi tạo thanh toán");
        }
    }
}
