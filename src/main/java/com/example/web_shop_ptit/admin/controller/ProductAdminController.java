package com.example.web_shop_ptit.admin.controller;

import com.example.web_shop_ptit.admin.entity.*;
import com.example.web_shop_ptit.admin.exception.AddProductException;
import com.example.web_shop_ptit.admin.exception.DeleteProductException;
import com.example.web_shop_ptit.admin.exception.EditProductException;
import com.example.web_shop_ptit.admin.service.CategoryManagementService;
import com.example.web_shop_ptit.admin.service.FilesStorageService;
import com.example.web_shop_ptit.admin.service.OrderManagementService;
import com.example.web_shop_ptit.admin.service.ProductManagementService;
import com.example.web_shop_ptit.client.entity.ProductSizes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/")
public class ProductAdminController {
    @Autowired
    private ProductManagementService productManagementService;

    @Autowired
    private CategoryManagementService categoryManagementService;

    @Autowired
    private FilesStorageService filesStorageService;

    @Autowired
    private OrderManagementService orderManagementService;
    @GetMapping("/product")
    public String productPage(Model model, HttpServletRequest request) {
        List<ProductManagement> products = productManagementService.listAll();
        model.addAttribute("products", products);
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("adminInfo");
        model.addAttribute("role", admin.getRole());
        model.addAttribute("name", admin.getUsername());
        return "web_admin/product";
    }

    @RequestMapping("addProductForm")
    public String addProductForm(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("adminInfo");
        if(!Objects.equals(admin.getRole(), "admin") && !Objects.equals(admin.getRole(), "manager")){
            redirectAttributes.addFlashAttribute("err", "Bạn không có quyền thực hiện thao tác này!");
            return "redirect:/admin/product";
        }
        List<CategoryManagement> categories = categoryManagementService.listAll();

        ProductManagement product = new ProductManagement();
        model.addAttribute("categories", categories);
        model.addAttribute("operation", "add");
        model.addAttribute("product", product);

        return "web_admin/productForm";
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }



    @PostMapping("/product/add")
    public String saveProduct(Model model, RedirectAttributes redirectAttributes,
                              @Valid @ModelAttribute("product") ProductManagement product,
                              @RequestParam("priceField") String priceField,
                              @RequestParam("DanhMucSanPham") int DanhMucSanPham,
                              @RequestParam("SoLuongSP_S") String SoLuongSP_S,
                              @RequestParam("SoLuongSP_M") String SoLuongSP_M,
                              @RequestParam("SoLuongSP_L") String SoLuongSP_L,
                              @RequestParam("SoLuongSP_XL") String SoLuongSP_XL,
                              @RequestParam("SoLuongSP_XXL") String SoLuongSP_XXL,
                               @RequestParam("fileToUpload") MultipartFile[] fileToUpload,
                                BindingResult bindingResult) {
        try {
            boolean check = true;
            boolean checkPrice = true;
            List<CategoryManagement> categories = categoryManagementService.listAll();
            // Xử lý dữ liệu tại đây
            model.addAttribute("categories", categories);
            model.addAttribute("priceField", priceField);
            model.addAttribute("DanhMucSanPham", DanhMucSanPham);
            model.addAttribute("SoLuongSP_S", SoLuongSP_S);
            model.addAttribute("SoLuongSP_M", SoLuongSP_M);
            model.addAttribute("SoLuongSP_L", SoLuongSP_L);
            model.addAttribute("SoLuongSP_XL", SoLuongSP_XL);
            model.addAttribute("SoLuongSP_XXL", SoLuongSP_XXL);

            model.addAttribute("operation", "add");
            // check blank tên sản phẩm
            if (product.getName().isEmpty()) {
                check = false;
                bindingResult.rejectValue("name", "product.name", "Vui lòng nhập tên sản phẩm");
            }

            // check giá hợp lệ
            if(!isNumeric(priceField)){
                check = false;
                bindingResult.rejectValue("price", "product.price", "Vui lòng nhập giá là số không âm");
            }

            // check số lượng
            if(!isNumeric(SoLuongSP_S) || !isNumeric(SoLuongSP_M) || !isNumeric(SoLuongSP_L) || !isNumeric(SoLuongSP_XL) || !isNumeric(SoLuongSP_XXL)){
                check = false;
                bindingResult.rejectValue("productSizes", "product.productSizes", "Vui lòng nhập số lượng là số không âm");
            }

            // check mô tả

            if (product.getDescription().isEmpty()) {
                check = false;
                bindingResult.rejectValue("description", "product.description", "Vui lòng nhập mô tả");
            }

            // check màu
            if (product.getColor() == null) {
                check = false;
                bindingResult.rejectValue("color", "product.color", "Vui lòng chọn màu");
            }
            // check hình

            int jpegPngCount = 0;

            for (MultipartFile file : fileToUpload) {
                String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
                if (extension != null && (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))) {
                    jpegPngCount++;
                }
            }

            if (jpegPngCount != 4) {
                // Xử lý khi không đủ số lượng hoặc loại file không đúng
                check = false;
                bindingResult.rejectValue("productImages", "product.productImages", "Vui lòng chọn 4 file ảnh");
            }


            if(!check){
                return "web_admin/productForm";
            }

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
                fileNames.add(path);
            });

            String MaSanPham = generateProductCode();
            // delay để tạo mã sản phẩm
            Thread.sleep(1000);

            Long SL_S = Long.parseLong(SoLuongSP_S);
            Long SL_M = Long.parseLong(SoLuongSP_M);
            Long SL_L = Long.parseLong(SoLuongSP_L);
            Long SL_XL = Long.parseLong(SoLuongSP_XL);
            Long SL_XXL = Long.parseLong(SoLuongSP_XXL);
            Long price = Long.parseLong(priceField);
            productManagementService.saveProduct(product, price ,MaSanPham, SL_S, SL_M, SL_L, SL_XL, SL_XXL, fileNames, category);

            redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
            return "redirect:/admin/product";
        }
        catch (AddProductException e){
            model.addAttribute("err", e.getMessage());
            return "web_admin/productForm";
        }
        catch (Exception e) {
            model.addAttribute("err", e.getMessage());
            return "web_admin/productForm";
        }
    }

    @PostMapping("updateProductForm")
    public String updateProductForm(HttpServletRequest request, RedirectAttributes redirectAttributes, @RequestParam("MaSanPham") String MaSanPham,
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
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("adminInfo");
            if(!Objects.equals(admin.getRole(), "admin") && !Objects.equals(admin.getRole(), "manager")){
                redirectAttributes.addFlashAttribute("err", "Bạn không có quyền thực hiện thao tác này!");
                return "redirect:/admin/product";
            }
            List<CategoryManagement> categories = categoryManagementService.listAll();
            // Xử lý dữ liệu tại đây
            model.addAttribute("categories", categories);
            model.addAttribute("operation", "edit");

            ProductManagement product = productManagementService.getProductByProductCode(MaSanPham);

            model.addAttribute("product", product);
            model.addAttribute("priceField", GiaSanPham);
            model.addAttribute("DanhMucSanPham", DanhMucSanPham);
            model.addAttribute("SoLuongSP_S", SoLuongSP_S);
            model.addAttribute("SoLuongSP_M", SoLuongSP_M);
            model.addAttribute("SoLuongSP_L", SoLuongSP_L);
            model.addAttribute("SoLuongSP_XL", SoLuongSP_XL);
            model.addAttribute("SoLuongSP_XXL", SoLuongSP_XXL);
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
    public String editProduct(Model model, RedirectAttributes redirectAttributes,
                              @Valid @ModelAttribute("product") ProductManagement product,
                              @RequestParam("priceField") String priceField,
                              @RequestParam("DanhMucSanPham") int DanhMucSanPham,
                              @RequestParam("SoLuongSP_S") String SoLuongSP_S,
                              @RequestParam("SoLuongSP_M") String SoLuongSP_M,
                              @RequestParam("SoLuongSP_L") String SoLuongSP_L,
                              @RequestParam("SoLuongSP_XL") String SoLuongSP_XL,
                              @RequestParam("SoLuongSP_XXL") String SoLuongSP_XXL,
                              @RequestParam("fileToUpload") MultipartFile[] fileToUpload,
                              BindingResult bindingResult) {
        try {
            boolean check = true;
            boolean checkImg = true;
            List<CategoryManagement> categories = categoryManagementService.listAll();
            // Xử lý dữ liệu tại đây
            model.addAttribute("categories", categories);
            model.addAttribute("priceField", priceField);
            model.addAttribute("DanhMucSanPham", DanhMucSanPham);
            model.addAttribute("SoLuongSP_S", SoLuongSP_S);
            model.addAttribute("SoLuongSP_M", SoLuongSP_M);
            model.addAttribute("SoLuongSP_L", SoLuongSP_L);
            model.addAttribute("SoLuongSP_XL", SoLuongSP_XL);
            model.addAttribute("SoLuongSP_XXL", SoLuongSP_XXL);


            // lấy tên image cũ
            ProductManagement oldProduct = productManagementService.getProductByProductCode(product.getProductCode());
            List<ProductImageManagement> oldProductImage = oldProduct.getProductImages();
            List<String> oldImgPath = new ArrayList<>();
            for (ProductImageManagement productImg : oldProductImage) {
                oldImgPath.add(productImg.getImage());
            }

            List<String> images = new ArrayList<>();

            for (MultipartFile file : fileToUpload) {
                String fileName = file.getOriginalFilename();
                if (fileName != null && oldImgPath.stream().anyMatch(name -> name.equals('.'+fileName))) {
                    images.add(fileName);
                }
            }

            if(images.size() == 4){
                model.addAttribute("images", images);
            }

            model.addAttribute("operation", "edit");
            // check blank tên sản phẩm
            if (product.getName().isEmpty()) {
                check = false;
                bindingResult.rejectValue("name", "product.name", "Vui lòng nhập tên sản phẩm");
            }

            // check giá hợp lệ
            if(!isNumeric(priceField)){
                check = false;
                bindingResult.rejectValue("price", "product.price", "Vui lòng nhập giá là số không âm");
            }

            // check số lượng
            if(!isNumeric(SoLuongSP_S) || !isNumeric(SoLuongSP_M) || !isNumeric(SoLuongSP_L) || !isNumeric(SoLuongSP_XL) || !isNumeric(SoLuongSP_XXL)){
                check = false;
                bindingResult.rejectValue("productSizes", "product.productSizes", "Vui lòng nhập số lượng là số không âm");
            }

            // check mô tả

            if (product.getDescription().isEmpty()) {
                check = false;
                bindingResult.rejectValue("description", "product.description", "Vui lòng nhập mô tả");
            }

            // check màu
            if (product.getColor() == null) {
                check = false;
                bindingResult.rejectValue("color", "product.color", "Vui lòng chọn màu");
            }
            // check hình

            int jpegPngCount = 0;

            for (MultipartFile file : fileToUpload) {
                String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
                if (extension != null && (extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))) {
                    jpegPngCount++;
                }
            }

            if (jpegPngCount != 4) {
                // Xử lý khi không đủ số lượng hoặc loại file không đúng
                check = false;
                bindingResult.rejectValue("productImages", "product.productImages", "Vui lòng chọn 4 file ảnh");
            }


            if(!check){
                return "web_admin/productForm";
            }

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
                fileNames.add(path);
            });
            for (ProductImageManagement image : oldProductImage) {
                filesStorageService.deleteFile(image.getImage());
            }

            Long SL_S = Long.parseLong(SoLuongSP_S);
            Long SL_M = Long.parseLong(SoLuongSP_M);
            Long SL_L = Long.parseLong(SoLuongSP_L);
            Long SL_XL = Long.parseLong(SoLuongSP_XL);
            Long SL_XXL = Long.parseLong(SoLuongSP_XXL);
            Long price = Long.parseLong(priceField);
            productManagementService.updateProduct(product, price ,SL_S, SL_M, SL_L, SL_XL, SL_XXL, fileNames, category);

//            for (ProductImageManagement image : oldProductImage) {
//                filesStorageService.deleteFile(image.getImage());
//            }

            redirectAttributes.addFlashAttribute("success", "Sửa sản phẩm thành công!");
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
    public String deleteCategoryPage(HttpServletRequest request, @RequestParam String MaSanPham, RedirectAttributes redirectAttributes) {
        try {
            HttpSession session = request.getSession();
            Admin admin = (Admin) session.getAttribute("adminInfo");
            if(!Objects.equals(admin.getRole(), "admin") && !Objects.equals(admin.getRole(), "manager")){
                redirectAttributes.addFlashAttribute("err", "Bạn không có quyền thực hiện thao tác này!");
                return "redirect:/admin/product";
            }
            List<OrderManagement> orders = orderManagementService.listOrderNotDelivered(MaSanPham);

            if(!orders.isEmpty()){
                redirectAttributes.addFlashAttribute("err", "Không thể xóa do sản phẩm nằm trong một đơn hàng!");
                return "redirect:/admin/product";
            }
            ProductManagement product = productManagementService.getProductByProductCode(MaSanPham);

            List<ProductImageManagement> images = product.getProductImages();

            for (ProductImageManagement image : images) {
                filesStorageService.deleteFile(image.getImage().substring(1));
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
