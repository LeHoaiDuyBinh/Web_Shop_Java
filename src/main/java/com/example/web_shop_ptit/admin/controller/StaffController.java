package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.*;
import com.example.web_shop_ptit.admin.exception.DeleteProductException;
import com.example.web_shop_ptit.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/")
public class StaffController {
    @Autowired
    private AdminService adminService;
    @GetMapping("/staff")
    public String staffPage(Model model) {
        List<Admin> admins = adminService.listAll();

        model.addAttribute("admins", admins);
        return "web_admin/staff";
    }

    @RequestMapping("addStaffForm")
    public String addStaffForm(Model model) {
        model.addAttribute("staff", new Admin());
        model.addAttribute("operation", "add");

        return "web_admin/staffForm";
    }

//    @PostMapping("/staff/add")
//    public String saveStaff(@RequestParam String username, @RequestParam String password, @RequestParam String role,
//                            RedirectAttributes redirectAttributes, BindingResult errors) {
//        try {
//            if(username.isEmpty()){
//                errors.rejectValue("username", "error.email", "Không được để trống");
//                return "web_admin/staffForm";
//            }
//            adminService.saveStaff(username, password, role);
//            redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công!");
//            return "redirect:/admin/staff";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("err", "Lỗi");
//            System.out.println(e.getMessage());
//        }
//        return "redirect:/admin/staff";
//    }

    @PostMapping("/staff/add")
    public String saveStaff(Model model, RedirectAttributes redirectAttributes,
                            @Valid @ModelAttribute("staff") Admin staff,
                            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("operation", "add");
                return "web_admin/staffForm";
            }
            Admin tmp = adminService.findAdminByUsername(staff.getUsername());
            if(staff.getPassword().isEmpty()){
                bindingResult.rejectValue("password", "staff.password", "Không được để trống mật khẩu");
                model.addAttribute("operation", "add");
                return "web_admin/staffForm";
            }

            if(tmp != null){
                bindingResult.rejectValue("username", "staff.username", "Đã tồn tại username này");
                model.addAttribute("operation", "add");
                return "web_admin/staffForm";
            }
            adminService.saveStaff(staff);
            redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công!");
            return "redirect:/admin/staff";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
            return "redirect:/admin/staff";
        }
    }


    @PostMapping("/updateStaffForm")
    public String updateStaffForm(@RequestParam String username, @RequestParam String role, Model model) {
        try {
            Admin staff = new Admin();
            staff.setUsername(username);
            staff.setRole(role);
            model.addAttribute("operation", "edit");
            model.addAttribute("staff", staff);
            return "web_admin/staffForm";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
            return "web_admin/staffForm";
        }
    }

    @PostMapping("/staff/edit")
    public String editStaff(Model model, RedirectAttributes redirectAttributes,
                            @Valid @ModelAttribute("staff") Admin staff,
                            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("operation", "edit");
                return "web_admin/staffForm";
            }
            Admin tmp = adminService.findAdminByUsername(staff.getUsername());
            System.out.println(staff.getUsername());

            if(tmp != null && !Objects.equals("admin", tmp.getRole())){
                adminService.updateStaff(staff);
                redirectAttributes.addFlashAttribute("success", "Sửa thông tin nhân viên thành công!");
                return "redirect:/admin/staff";
            }
            else{
                redirectAttributes.addFlashAttribute("err", "Không được sửa admin");
                return "redirect:/admin/staff";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Lỗi");
            return "redirect:/admin/staff";
        }
    }

    @PostMapping("/staff/delete")
    public String deleteStaff(@RequestParam String username, RedirectAttributes redirectAttributes) {
        try {
            Admin tmp = adminService.findAdminByUsername(username);
            if(tmp != null && !Objects.equals(tmp.getRole(), "admin")){
                adminService.deleteStaff(username);
                redirectAttributes.addFlashAttribute("success", "Xóa nhân viên thành công!");
                return "redirect:/admin/staff";
            }
            else{
                redirectAttributes.addFlashAttribute("err", "Không được xóa admin");
                return "redirect:/admin/staff";
            }
        }
        catch (DeleteProductException e){
            redirectAttributes.addFlashAttribute("err", e.getMessage());
            return "redirect:/admin/staff";
        }
    }
}