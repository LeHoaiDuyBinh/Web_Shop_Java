package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.OrderManagement;
import com.example.web_shop_ptit.admin.exception.MoveOrderException;
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

    @GetMapping("/order")
    public String orderPage(Model model) {
        List<OrderManagement> orders = orderManagementService.listAll();

        model.addAttribute("orders", orders);
        return "web_admin/order";
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
        OrderManagement tmp = orderManagementService.findOrderManagementByOrderCode(MaDonHang);
        if(Objects.equals(tmp.getState(), "delivered") || Objects.equals(tmp.getState(), "cancelled")){
            redirectAttributes.addFlashAttribute("err", "Không thể đổi trạng thái đơn đã hủy hoặc đã thanht toán");
            return "redirect:/admin/order";
        }

        if(Objects.equals(tmp.getState(), "pending") && Objects.equals(state, "delivered")){
            redirectAttributes.addFlashAttribute("err", "Chỉ có thể hủy hoặc vận chuyển đơn đang xử lý");
            return "redirect:/admin/order";
        }

        if(Objects.equals(tmp.getState(), "delivering") && !Objects.equals(state, "delivered")){
            redirectAttributes.addFlashAttribute("err", "Chỉ có thể thanh toán đơn đang vận chuyển");
            return "redirect:/admin/order";
        }

        try {
            if(!state.equals("delivered")){
                if(state.equals("cancelled")){
                    orderManagementService.moveOrderToOrderHistory(MaDonHang, state);
                }
                else{
                    orderManagementService.updateOrderState(MaDonHang, state);
                }
            }
            else{
                orderManagementService.moveOrderToOrderHistory(MaDonHang, state);
            }
            redirectAttributes.addFlashAttribute("success", "Bạn đã " + text + " thành công!");
            return "redirect:/admin/order";
        }
        catch (MoveOrderException e){
            redirectAttributes.addFlashAttribute("err", e.getMessage());
            return "redirect:/admin/order";
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("err", "Lỗi");
            return "redirect:/admin/order";
        }
    }
}
