package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.service.CategoryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/")
public class CategoryAdminController {

    @Autowired
    private CategoryManagementService categoryManagementService;

    @GetMapping("/category")
    public String categoryPage(Model model) {
        List<CategoryManagement> categories = categoryManagementService.listAll();

        model.addAttribute("categories", categories);

        // Trả về tên của view hoặc đường dẫn
        return "web_admin/category";
    }
    @PostMapping("/add")
    public String saveCategory(@RequestParam String tenDanhMuc, @RequestParam int danhMucCha, Model model) {
        try {
            CategoryManagement categoryParent = categoryManagementService.getParentCategoryById(danhMucCha);
//            System.out.println(categoryParent.getName());
            categoryManagementService.saveCategory(tenDanhMuc, categoryParent);
            return "redirect:/admin/category";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/category";
    }

    @PostMapping("/edit")
    public String editCategory(@RequestParam int maDanhMuc, @RequestParam String tenDanhMuc, @RequestParam int danhMucCha, Model model) {
        try {
            CategoryManagement categoryParent = categoryManagementService.getParentCategoryById(danhMucCha);

            categoryManagementService.updateCategory(maDanhMuc, tenDanhMuc, categoryParent);
            return "redirect:/admin/category";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
        }
        return "redirect:/admin/category";
    }

        @RequestMapping("addCategory")
        public String addCategoryPage(Model model) {
            List<CategoryManagement> categories = categoryManagementService.listAll();

            model.addAttribute("categories", categories);
            model.addAttribute("operation", "add"); // hoặc "edit" tùy thuộc vào hoạt động

            return "/web_admin/categoryForm";
    }

        @RequestMapping("updateCategory")
        public String updateCategoryPage(@RequestParam int maDanhMuc,
                                     @RequestParam String tenDanhMuc,
                                     @RequestParam int danhMucCha,
                                     Model model) {
            try {
                model.addAttribute("maDanhMuc", maDanhMuc);
                model.addAttribute("tenDanhMuc", tenDanhMuc);
                model.addAttribute("danhMucCha", danhMucCha);
                List<CategoryManagement> categories = categoryManagementService.listAll();

                model.addAttribute("categories", categories);
                model.addAttribute("operation", "edit"); // hoặc "edit" tùy thuộc vào hoạt động

                return "web_admin/categoryForm";
            } catch (Exception e) {
                model.addAttribute("err", "Lỗi");
                System.out.println(e.getMessage());
            }
            return "web_admin/categoryForm";
        }

        @RequestMapping("deleteCategory")
        public String deleteCategoryPage(@RequestParam int categoryId) {
            categoryManagementService.deleteCategory(categoryId);
            return "redirect:/admin/category";
        }
}

