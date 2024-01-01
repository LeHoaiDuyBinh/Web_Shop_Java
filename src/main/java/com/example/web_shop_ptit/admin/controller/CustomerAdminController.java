package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.Admin;
import com.example.web_shop_ptit.admin.entity.CustomerManagement;
import com.example.web_shop_ptit.admin.service.CustomerManagementService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/")
public class CustomerAdminController {
    @Autowired
    private CustomerManagementService customerManagementService;
    @GetMapping("/customer")
    public String customerPage(Model model, HttpServletRequest request) {
        List<CustomerManagement> customers = customerManagementService.listAll();

        model.addAttribute("customers", customers);
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("adminInfo");
        model.addAttribute("role", admin.getRole());
        // Trả về tên của view hoặc đường dẫn
        return "web_admin/customer";
    }
    @RequestMapping("/updateCustomer")
    public String updateCustomerPage(@RequestParam String Mail,
                                     Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("adminInfo");
            if(!Objects.equals(admin.getRole(), "admin") && !Objects.equals(admin.getRole(), "manager")){
                redirectAttributes.addFlashAttribute("err", "Bạn không có quyền thực hiện thao tác này!");
                return "redirect:/admin/customer";
            }
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
