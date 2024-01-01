package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.*;
import com.example.web_shop_ptit.client.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/auth/")
public class PaymentController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping("/payment")
    public String paymentPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = categoryService.listAll();
        model.addAttribute("listCategorys", listCategory);
        ShoppingCart cart = shoppingCartService.findByEmail(customerInfo.getEmail());
        if (customerInfo != null){
            System.out.println("success");
            List<CartItem> cartItems = cartItemService.findByCartItem(cart.getCartCode());
            model.addAttribute("checkSession", "1");
            model.addAttribute("listCartItems", cartItems);
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");
            return "web_client/login";
        }
        return "web_client/delivery";
    }

    @PostMapping("/payment")
    public String saveDataPayment(Model model, HttpServletRequest request,
                                  @RequestParam String provinceText,
                                  @RequestParam String districtText,
                                  @RequestParam String wardText,
                                  @RequestParam String address) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = categoryService.listAll();
        model.addAttribute("listCategorys", listCategory);
        ShoppingCart cart = shoppingCartService.findByEmail(customerInfo.getEmail());
        if (customerInfo != null){
            System.out.println("success");
            List<CartItem> cartItems = cartItemService.findByCartItem(cart.getCartCode());
            model.addAttribute("checkSession", "1");
            model.addAttribute("listCartItems", cartItems);
            System.out.println(provinceText + " " + districtText + " " + " " + wardText + " " + address);
            List<Order> orderList = orderService.listAll();
            List<OrderHistory> orderHistoryList = orderHistoryService.listAll();
            String orderCount = String.valueOf(orderList.size() + orderHistoryList.size() + 1);
            orderCount = "order_" + orderCount;
            LocalDateTime currentDateTime = LocalDateTime.now();
            Long totalPrice = 0L;
            for (CartItem cartItem : cartItems){
                totalPrice += cartItem.getTotalPrice();
            }
            String address_result = address + ", " + wardText + ", " + districtText + ", " + provinceText;
            orderService.saveOrder(orderCount, currentDateTime, totalPrice, customerInfo.getEmail(), address_result);
            orderItemService.saveOrderItem(orderCount, cartItems);
            cartItemService.deleteCartItem(cart.getCartCode());
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");
            return "web_client/login";
        }
        return "redirect:/auth/orderManagement";
    }

}
