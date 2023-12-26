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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        if (customerInfo != null){
            System.out.println("success");
            model.addAttribute("checkSession", "1");
            model.addAttribute("CustomerInfo", customerInfo);
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");

        }

//        System.out.println("---- INFORMATION ----");
//        System.out.println(customerInfo.getFullname());
//        model.addAttribute("CustomerInfo", customerInfo);
        return "web_client/informationUser";
    }

    @RequestMapping("changePasswordUser")
    public String showChangePasswordUser(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        List<Category> listCategory = service.listAll();
        model.addAttribute("listCategorys", listCategory);
        if (customerInfo != null){
            System.out.println("success");
            model.addAttribute("checkSession", "1");
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");

        }
        return "web_client/userChangePassword";
    }

    @PostMapping("informationUser")
    public String updateInformationUser(@RequestParam String fullName, @RequestParam String phoneNumber,
                                        HttpServletRequest request, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        if (customerInfo != null){
            customerInfo.setFullname(fullName);
            customerInfo.setPhone(phoneNumber);
            customerService.updateInfomation(customerInfo.getEmail(), fullName, phoneNumber);

            redirectAttributes.addFlashAttribute("success", "1");
            return "redirect:/auth/informationUser";
        }else{
            return "redirect:/auth/login";
        }
    }

    @PostMapping("changePasswordUser")
    public String changePasswordUser(@RequestParam String passwordOld ,@RequestParam String passwordNew, @RequestParam String passwordConfirm,
                                        HttpServletRequest request, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        if (customerInfo != null){
            if (customerInfo.getPassword().equals(hashPasswordSHA256(passwordOld))){
                if(passwordNew.equals(passwordConfirm)){
                    customerService.updatePassword(customerInfo.getEmail(), passwordNew);
                    redirectAttributes.addFlashAttribute("success", "1");
                }else{
                    redirectAttributes.addFlashAttribute("errorConfirm", "1");

                }
            } else {
                redirectAttributes.addFlashAttribute("error", "1");
            }

            return "redirect:/auth/changePasswordUser";
        }else{
            return "redirect:/auth/login";
        }
    }

    private String hashPasswordSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi byte array thành dạng hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý exception nếu cần
            e.printStackTrace();
            return null;
        }
    }
}
