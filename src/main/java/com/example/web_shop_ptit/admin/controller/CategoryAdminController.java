package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.service.CategoryManagementService;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveCategory(Model model, RedirectAttributes redirectAttributes,
                               @RequestParam int danhMucCha,
                               @Valid @ModelAttribute("category") CategoryManagement category,
                               BindingResult result) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("categories", categoryManagementService.listAll());
                model.addAttribute("operation", "add");
                model.addAttribute("danhMucCha", danhMucCha);
                return "web_admin/categoryForm";
            }
            CategoryManagement danhMucCha1 = categoryManagementService.getParentCategoryById(danhMucCha);
            category.setParentCategoryManagement(danhMucCha1);
            categoryManagementService.saveCategory(category.getName(), category.getParentCategoryManagement());
            redirectAttributes.addFlashAttribute("success", "Thêm danh mục thành công");
            return "redirect:/admin/category";
        } catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            return "redirect:/admin/category";
        }
    }

    @PostMapping("/edit")
    public String editCategory(Model model, RedirectAttributes redirectAttributes,
                               @RequestParam int danhMucCha,
                               @Valid @ModelAttribute("category") CategoryManagement category,
                               BindingResult result) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("categories", categoryManagementService.listAll());
                model.addAttribute("operation", "edit");
                model.addAttribute("category",category);
                model.addAttribute("danhMucCha", danhMucCha);
                return "web_admin/categoryForm";
            }
            CategoryManagement danhMucCha1 = categoryManagementService.getParentCategoryById(danhMucCha);
            category.setParentCategoryManagement(danhMucCha1);
            categoryManagementService.updateCategory(category.getCategoryId(), category.getName(), category.getParentCategoryManagement());
            redirectAttributes.addFlashAttribute("success", "Sửa danh mục thành công");
            return "redirect:/admin/category";
        } catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            return "redirect:/admin/category";
        }
    }
    @RequestMapping("addCategory")
    public String addCategoryPage(Model model) {
        List<CategoryManagement> categories = categoryManagementService.listAll();
        model.addAttribute("categories", categories);
        model.addAttribute("operation", "add");
        model.addAttribute("category", new CategoryManagement());
        return "web_admin/categoryForm";
    }

    @RequestMapping("updateCategory")
    public String updateCategoryPage(@RequestParam int maDanhMuc,
                                     @RequestParam String tenDanhMuc,
                                     @RequestParam int danhMucCha,
                                     Model model) {
        try {
            CategoryManagement category = new CategoryManagement();
            category.setCategoryId(maDanhMuc);
            category.setName(tenDanhMuc);
            CategoryManagement IdDanhMuc = categoryManagementService.getParentCategoryById(danhMucCha);
            category.setParentCategoryManagement(IdDanhMuc);
            model.addAttribute("category",category);
            model.addAttribute("danhMucCha", danhMucCha);
            List<CategoryManagement> categories = categoryManagementService.listAll();
            model.addAttribute("categories", categories);
            model.addAttribute("operation", "edit");
            return "web_admin/categoryForm";
        } catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            System.out.println(e.getMessage());
        }
        return "web_admin/categoryForm";
    }

    @RequestMapping("deleteCategory")
    public String deleteCategoryPage(@RequestParam int categoryId,RedirectAttributes redirectAttributes) {
        categoryManagementService.deleteCategory(categoryId);
        redirectAttributes.addFlashAttribute("success", "Xóa danh mục thành công");
        return "redirect:/admin/category";
    }
}