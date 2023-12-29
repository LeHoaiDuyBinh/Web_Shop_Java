package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.*;
import com.example.web_shop_ptit.client.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/auth/")
public class CartItemController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @RequestMapping("cart")
    public String cartPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = categoryService.listAll();
        model.addAttribute("listCategorys", listCategory);
        ShoppingCart cart = shoppingCartService.findByEmail(customerInfo.getEmail());
        System.out.println(cart.getCartCode());
        if (customerInfo != null){
            System.out.println("success");
            model.addAttribute("checkSession", "1");
            List<CartItem> cartItems = cartItemService.findByCartItem(cart.getCartCode());
            model.addAttribute("listCartItems", cartItems);
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");

        }
        return "web_client/cart";
    }

}
