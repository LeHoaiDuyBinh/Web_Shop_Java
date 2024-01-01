package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CustomerManagement;
import com.example.web_shop_ptit.admin.service.CustomerManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @RequestMapping("/updateCustomer")
    public String updateCustomerPage(@RequestParam String Mail,
                                     Model model) {
        try {
            CustomerManagement customer = customerManagementService.getCustomerByEmail(Mail);
            model.addAttribute("customer", customer);
            model.addAttribute("operation", "editCustomer");
            return "web_admin/customerForm";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
            return "web_admin/customerForm";
        }
    }

    @PostMapping("/editCustomer")
    public String editCustomer(Model model, RedirectAttributes redirectAttributes,
                               @Valid @ModelAttribute("customer") CustomerManagement customer,
                               BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("operation", "editCustomer");
                return "web_admin/customerForm";
            }
            customerManagementService.updateCustomer(customer.getEmail(), customer.getFullName(), customer.getPhone());
            redirectAttributes.addFlashAttribute("success", "Sửa thông tin khách hàng thành công");
            return "redirect:/admin/customer";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Lỗi");
            return "redirect:/admin/customer";
        }
    }

}
