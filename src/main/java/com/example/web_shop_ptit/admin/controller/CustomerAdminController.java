package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CustomerManagement;
import com.example.web_shop_ptit.admin.service.CustomerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class CustomerAdminController {
    @Autowired
    private CustomerManagementService customerManagementService;
    @GetMapping("/customer")
    public String customerPage(Model model) {
        List<CustomerManagement> customers = customerManagementService.listAll();

        model.addAttribute("customers", customers);
        // Trả về tên của view hoặc đường dẫn
        return "web_admin/customer";
    }
    @PostMapping("/editCustomer")
    public String editCustomer(@RequestParam(name = "Mail") String email,
                               @RequestParam(name = "TenKhachHang") String fullname,
                               @RequestParam(name = "SDT") String phone,
                               Model model) {
        try {
            CustomerManagement customer = customerManagementService.getCustomerByEmail(email);
            customerManagementService.updateCustomer(email, fullname, phone);
            return "redirect:/admin/customer";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/customer";
    }

    @RequestMapping("updateCustomer")
    public String updateCustomerPage(@RequestParam String Mail,
                                    @RequestParam String TenKhachHang,
                                     @RequestParam String SDT,
                                     Model model) {
        try {
            model.addAttribute("Mail", Mail);
            model.addAttribute("TenKhachHang", TenKhachHang);
            model.addAttribute("SDT", SDT);
            List<CustomerManagement> customers = customerManagementService.listAll();

            model.addAttribute("customers", customers);
            model.addAttribute("operation", "editCustomer");
            return "web_admin/customerForm";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
        }
        return "web_admin/customerForm";
    }
}
