package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.ProductImageManagement;
import com.example.web_shop_ptit.admin.entity.ProductManagement;
import com.example.web_shop_ptit.admin.exception.AddProductException;
import com.example.web_shop_ptit.admin.exception.DeleteProductException;
import com.example.web_shop_ptit.admin.exception.EditProductException;
import com.example.web_shop_ptit.admin.service.CategoryManagementService;
import com.example.web_shop_ptit.admin.service.FilesStorageService;
import com.example.web_shop_ptit.admin.service.ProductManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class ProductAdminController {
    @Autowired
    private ProductManagementService productManagementService;

    @Autowired
    private CategoryManagementService categoryManagementService;

    @Autowired
    private FilesStorageService filesStorageService;
    @GetMapping("/product")
    public String productPage(Model model) {
        List<ProductManagement> products = productManagementService.listAll();
        model.addAttribute("products", products);
        return "web_admin/product";
    }

    @RequestMapping("addProductForm")
    public String addProductForm(Model model) {
        List<CategoryManagement> categories = categoryManagementService.listAll();

        model.addAttribute("categories", categories);
        model.addAttribute("operation", "add");

        return "web_admin/productForm";
    }



    @PostMapping("/product/add")
    public String saveProduct(@RequestParam("TenSanPham") String TenSanPham,
                               @RequestParam("GiaSanPham") Long GiaSanPham,
                               @RequestParam("DanhMucSanPham") int DanhMucSanPham,
                               @RequestParam("color") String color,
                               @RequestParam("SoLuongSP_S") Long SoLuongSP_S,
                               @RequestParam("SoLuongSP_M") Long SoLuongSP_M,
                               @RequestParam("SoLuongSP_L") Long SoLuongSP_L,
                               @RequestParam("SoLuongSP_XL") Long SoLuongSP_XL,
                               @RequestParam("SoLuongSP_XXL") Long SoLuongSP_XXL,
                               @RequestParam("MoTa") String MoTa,
                               @RequestParam("fileToUpload") MultipartFile[] fileToUpload,
                               Model model, RedirectAttributes redirectAttributes) {
        try {
            List<CategoryManagement> categories = categoryManagementService.listAll();
            // Xử lý dữ liệu tại đây
            model.addAttribute("categories", categories);
            model.addAttribute("operation", "add");

            CategoryManagement category = categoryManagementService.getParentCategoryById(DanhMucSanPham);
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(fileToUpload).stream().forEach(file -> {
                String newFileName = generateImgName(file); // Tạo tên file mới
                try {
                    // delay để tạo tên ảnh
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String path = filesStorageService.saveWithNewName(file, newFileName);
                fileNames.add('.'  + path);
            });

            String MaSanPham = generateProductCode();
            // delay để tạo mã sản phẩm
            Thread.sleep(1000);

            productManagementService.saveProduct(MaSanPham, TenSanPham, GiaSanPham,
                    color, SoLuongSP_S, SoLuongSP_M, SoLuongSP_L, SoLuongSP_XL, SoLuongSP_XXL, MoTa, fileNames, category);

            redirectAttributes.addFlashAttribute("sucess", "Thêm sản phẩm thành công!");
            return "redirect:/admin/product";
        }
        catch (AddProductException e){
            model.addAttribute("err", e.getMessage());
            return "web_admin/productForm";
        }
        catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            return "web_admin/productForm";
        }
    }

    @PostMapping("updateProductForm")
    public String updateProductForm(@RequestParam("MaSanPham") String MaSanPham,
                              @RequestParam("TenSanPham") String TenSanPham,
                              @RequestParam("GiaSanPham") Long GiaSanPham,
                              @RequestParam("DanhMucSanPham") int DanhMucSanPham,
                              @RequestParam("color") String color,
                              @RequestParam("SoLuongSP_S") Long SoLuongSP_S,
                              @RequestParam("SoLuongSP_M") Long SoLuongSP_M,
                              @RequestParam("SoLuongSP_L") Long SoLuongSP_L,
                              @RequestParam("SoLuongSP_XL") Long SoLuongSP_XL,
                              @RequestParam("SoLuongSP_XXL") Long SoLuongSP_XXL,
                              @RequestParam("MoTa") String MoTa,
                              @RequestParam("imageInput") String imageInput,
                              @RequestParam("imageInput2") String imageInput2,
                              @RequestParam("imageInput3") String imageInput3,
                              @RequestParam("imageInput4") String imageInput4,
                              Model model) {
        try {
            List<CategoryManagement> categories = categoryManagementService.listAll();
            // Xử lý dữ liệu tại đây
            model.addAttribute("categories", categories);
            model.addAttribute("operation", "edit");

            model.addAttribute("MaSanPham",MaSanPham);
            model.addAttribute("TenSanPham",TenSanPham);
            model.addAttribute("GiaSanPham",GiaSanPham);
            model.addAttribute("DanhMucSanPham",DanhMucSanPham);
            model.addAttribute("color",color);
            model.addAttribute("SoLuongSP_S",SoLuongSP_S);
            model.addAttribute("SoLuongSP_M",SoLuongSP_M);
            model.addAttribute("SoLuongSP_L", SoLuongSP_L);
            model.addAttribute("SoLuongSP_XL", SoLuongSP_XL);
            model.addAttribute("SoLuongSP_XXL", SoLuongSP_XXL);
            model.addAttribute("MoTa", MoTa);
            List<String> images = new ArrayList<>();
            images.add(imageInput);
            images.add(imageInput2);
            images.add(imageInput3);
            images.add(imageInput4);
            model.addAttribute("images", images);
        }
        catch (Exception e) {
            model.addAttribute("err", "Lỗi");
            return "web_admin/productForm";
        }
        return "web_admin/productForm";
    }

    @PostMapping("/product/edit")
    public String editProduct(@RequestParam("MaSanPham") String MaSanPham,
                              @RequestParam("TenSanPham") String TenSanPham,
                              @RequestParam("GiaSanPham") Long GiaSanPham,
                              @RequestParam("DanhMucSanPham") int DanhMucSanPham,
                              @RequestParam("color") String color,
                              @RequestParam("SoLuongSP_S") Long SoLuongSP_S,
                              @RequestParam("SoLuongSP_M") Long SoLuongSP_M,
                              @RequestParam("SoLuongSP_L") Long SoLuongSP_L,
                              @RequestParam("SoLuongSP_XL") Long SoLuongSP_XL,
                              @RequestParam("SoLuongSP_XXL") Long SoLuongSP_XXL,
                              @RequestParam("MoTa") String MoTa,
                              @RequestParam("fileToUpload") MultipartFile[] fileToUpload,
                              Model model, RedirectAttributes redirectAttributes) {
        try {
            List<CategoryManagement> categories = categoryManagementService.listAll();
            // Xử lý dữ liệu tại đây
            model.addAttribute("categories", categories);
            model.addAttribute("operation", "add");

            CategoryManagement category = categoryManagementService.getParentCategoryById(DanhMucSanPham);
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(fileToUpload).stream().forEach(file -> {
                String newFileName = generateImgName(file); // Tạo tên file mới
                try {
                    // delay để tạo tên ảnh
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String path = filesStorageService.saveWithNewName(file, newFileName);
                fileNames.add('.'  + path);
            });
            ProductManagement product = productManagementService.getProductByProductCode(MaSanPham);

            List<ProductImageManagement> images = product.getProductImages();

            for (ProductImageManagement image : images) {
                filesStorageService.deleteFile(image.getImage());
            }


            productManagementService.updateProduct(MaSanPham, TenSanPham, GiaSanPham,
                    color, SoLuongSP_S, SoLuongSP_M, SoLuongSP_L, SoLuongSP_XL, SoLuongSP_XXL, MoTa, fileNames, category);

            redirectAttributes.addFlashAttribute("sucess", "Sửa sản phẩm thành công!");
            return "redirect:/admin/product";
        }
        catch (EditProductException e){
            model.addAttribute("err", e.getMessage());
            return "web_admin/productForm";
        }
        catch (Exception e) {
            model.addAttribute("err", "Có lỗi xảy ra");
            return "web_admin/productForm";
        }
    }

    @PostMapping("/product/delete")
    public String deleteCategoryPage(@RequestParam String MaSanPham, RedirectAttributes redirectAttributes) {
        try {
            ProductManagement product = productManagementService.getProductByProductCode(MaSanPham);

            List<ProductImageManagement> images = product.getProductImages();

            for (ProductImageManagement image : images) {
                filesStorageService.deleteFile(image.getImage());
            }

            productManagementService.deleteProduct(MaSanPham);
            redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công!");
            return "redirect:/admin/product";
        }
        catch (DeleteProductException e){
            redirectAttributes.addFlashAttribute("err", e.getMessage());
            return "redirect:/admin/product";
        }
    }

    private String generateImgName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        // Lấy thời gian hiện tại theo số giây
        long currentTimeInSeconds = Instant.now().getEpochSecond();

        String newName = "img" + currentTimeInSeconds + extension; // Tên file mới
        return newName;
    }

    private String generateProductCode() {

        // Lấy thời gian hiện tại theo số giây
        long currentTimeInSeconds = Instant.now().getEpochSecond();
        return "SP" + currentTimeInSeconds;
    }
}
