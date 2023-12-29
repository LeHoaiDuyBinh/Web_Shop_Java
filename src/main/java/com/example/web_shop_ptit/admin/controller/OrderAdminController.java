package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.OrderManagement;
import com.example.web_shop_ptit.admin.service.OrderManagementService;
import com.example.web_shop_ptit.admin.service.PaymentManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Controller
@RequestMapping("/admin/")
public class OrderAdminController {
    @Autowired
    private OrderManagementService orderManagementService;

    @Autowired
    private PaymentManagementService paymentManagementService;
    @GetMapping("/order")
    public String orderPage(Model model) {
        List<OrderManagement> orders = orderManagementService.listAll();

        model.addAttribute("orders", orders);
        return "web_admin/order";
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

    @PostMapping("/order/changeState")
    public String changeState(@RequestParam String MaDonHang, @RequestParam String action, RedirectAttributes redirectAttributes) {
        String state = "";
        String text = "";
        if(Objects.equals(action, "Cancel")){
            state = "cancelled";
            text = "hủy đơn";
        }
        else if(Objects.equals(action, "Delivery")){
            state = "delivering";
            text = "vận chuyển";
        }
        else if(Objects.equals(action, "Pay")){
            state = "delivered";
            text = "thanh toán";
        }
        if(!state.equals("delivered")){
            orderManagementService.updateOrderState(MaDonHang, state);
        }
        else{
            OrderManagement order = orderManagementService.findOrderManagementByOrderCode(MaDonHang);
            paymentManagementService.savePayment(order, generatePaymentCode());
            orderManagementService.updateOrderState(MaDonHang, state);
        }
        redirectAttributes.addFlashAttribute("success", "Bạn đã " + text + " thành công!");
        return "redirect:/admin/order";
    }
}
