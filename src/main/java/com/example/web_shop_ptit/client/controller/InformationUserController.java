package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Customer;
import com.example.web_shop_ptit.client.entity.RegistrationInfo;
import com.example.web_shop_ptit.client.service.CategoryService;
import com.example.web_shop_ptit.client.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/auth/")
public class InformationUserController {
    @Autowired
    private CategoryService service;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("informationUser")
    public String showInformationUser(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = service.listAll();
        model.addAttribute("listCategorys", listCategory);

        System.out.println("---- INFORMATION ----");
        System.out.println(customerInfo.getFullname());
        model.addAttribute("CustomerInfo", customerInfo);
        return "web_client/informationUser";
    }


}
