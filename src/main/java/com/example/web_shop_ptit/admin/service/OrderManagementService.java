package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.OrderManagement;
import com.example.web_shop_ptit.admin.entity.PaymentManagement;
import com.example.web_shop_ptit.admin.exception.MoveOrderException;
import com.example.web_shop_ptit.admin.exception.SaveOrderHistoryException;
import com.example.web_shop_ptit.admin.exception.SavePaymentException;
import com.example.web_shop_ptit.admin.repository.OrderManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
public class OrderManagementService {
    @Autowired
    private OrderManagementRepository repo;

    @Autowired
    private OrdersHistoryManagementService ordersHistoryManagementService;

    @Autowired
    private PaymentManagementService paymentManagementService;

    public List<OrderManagement> listAll() {
        return (List<OrderManagement>) repo.findAll();
    }
    public List<OrderManagement> listOrderNotDelivered(String MaSanPham) {
        return (List<OrderManagement>) repo.findOrdersByStatusAndProductCode(MaSanPham);
    }

    public OrderManagement findOrderManagementByOrderCode(String MaSanPham) {
        return repo.findOrderManagementByOrderCode(MaSanPham);
    }

    @Transactional
    public void updateOrderState(String MaDonHang, String action) {

        OrderManagement order = repo.findOrderManagementByOrderCode(MaDonHang);

        if (order != null){
            order.setState(action);
            repo.save(order);
        }
    }

    public static String generatePaymentCode() {
        // Sinh 5 ký tự ngẫu nhiên
        String randomChars = generateRandomChars();

        long currentTimeInSeconds = Instant.now().getEpochSecond();

        // Kết hợp chuỗi thời gian và chuỗi ký tự ngẫu nhiên để tạo mã thanh toán
        return randomChars + "_" + currentTimeInSeconds;
    }

    // Phương thức để sinh ký tự ngẫu nhiên
    private static String generateRandomChars() {
        // Các ký tự có thể được chọn
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(candidateChars.length());
            sb.append(candidateChars.charAt(index));
        }
        return sb.toString();
    }

    @Transactional
    public void moveOrderToOrderHistory(String MaDonHang, String state) throws Exception {

        OrderManagement order = repo.findOrderManagementByOrderCode(MaDonHang);
        try{
            if (order != null){
                order.setState(state);
                if(Objects.equals(state, "delivered")){
                    PaymentManagement payment = new PaymentManagement();
                    payment.setPaymentCode(generatePaymentCode());
                    payment.setOrder(order);
                    payment.setType("cash");
                    payment.setPaymentDate(new Date());
                    order.setPayment(payment);
                    repo.save(order);
                }
                ordersHistoryManagementService.saveOrderHistory(order);
                repo.deleteById(MaDonHang);
            }
        }
        catch (SavePaymentException | SaveOrderHistoryException e){
            throw new MoveOrderException(e.getMessage());
        }
        catch (Exception e){
            throw new MoveOrderException("Lỗi trong quá trình cập nhật đơn hàng và di chuyển đến lịch sử");
        }
    }


}
