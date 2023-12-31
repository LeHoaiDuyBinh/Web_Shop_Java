package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.*;
import com.example.web_shop_ptit.client.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/auth/")
public class ProductController {
    @Autowired private ProductService service;

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductSizeService productSizeService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;


    @GetMapping("product/{productCode}")
    public String showProductDetails(@PathVariable String productCode, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        if (customerInfo != null){
            System.out.println("success");
            model.addAttribute("checkSession", "1");
        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");
        }
        Product product = service.getProductByCode(productCode);
        if (product != null) {
            List<Category> listCategory = categoryService.listAll();
            model.addAttribute("listCategorys", listCategory);

            List<ProductImage> productImages = productImgService.listAll();
            List<ProductImage> listImg = new ArrayList<>();
            for (ProductImage productImage : productImages) {
                if (productImage.getId().getProductCode().equals(productCode)) {
                    listImg.add(productImage);
                }
            }
            List<Product> similarProducts = service.getSimilarProducts(product.getCategory().getCategoryId(), productCode);
            List<ProductImage> ImgSimilarProducts = new ArrayList<>();
            int counter = 0;
            for (Product similarProduct : similarProducts) {
                for (ProductImage ImgSimilar : productImages) {
                    if (ImgSimilar.getId().getProductCode().equals(similarProduct.getProductCode())) {
                        if (counter % 4 == 0) {
                            ImgSimilarProducts.add(ImgSimilar);
                        }
                        counter++;
                    }
                }
            }

            String nameParentCategory = "";
            Integer parentId = null;
            for (Category Category : listCategory) {
                if (product.getCategory().getCategoryId() == Category.getCategoryId()) {
                    parentId = Category.getParentCategoryId();
                    for (Category Category1 : listCategory) {
                        if (parentId == Category1.getCategoryId()) {
                            nameParentCategory = Category1.getName();
                        }
                    }
                }
            }

            if (similarProducts.size() > 0) {
                model.addAttribute("similarProducts", similarProducts);
            }

            List<ProductSizes> productSizes = productSizeService.findByProductCode(productCode);
            model.addAttribute("nameParentCategory", nameParentCategory);
            model.addAttribute("productImages", productImages);
            model.addAttribute("product", product);
            model.addAttribute("productCode1", productCode);
            model.addAttribute("listImg", listImg);
            model.addAttribute("ImgSimilarProducts", ImgSimilarProducts);
            model.addAttribute("productSizes", productSizes);
        } else {
            model.addAttribute("productNotFound", true);
        }
        return "web_client/product";
    }

    @GetMapping("/category")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Product> productList = service.findByNameContaining(keyword);
        List<ProductImage> listProductImg = productImgService.listAll();
        List<ProductImage> listImg = new ArrayList<>();
        int flag = 0;
        for ( ProductImage productImage : listProductImg) {
            if (flag == 0){
                listImg.add(productImage);
            }
            flag++;
            if(flag == 4){
                flag = 0;
            }
        }

        model.addAttribute("listProductImgs", listImg);
        model.addAttribute("keyword", keyword);
        model.addAttribute("productSearch", productList);
        return "web_client/category";
    }

    @PostMapping("/add-to-cart")
    public String updateCartItem(@RequestParam String productCode,
                                                 @RequestParam int quantity,
                                 @RequestParam(name = "size", required = false) Size size,
                                                 @RequestParam Long price,
                                                 HttpServletRequest request,
                                                 Model model) {
        HttpSession session = request.getSession();
        RegistrationInfo customerInfo = (RegistrationInfo) session.getAttribute("customerInfor");
        String action = request.getParameter("action");
        if(customerInfo == null){
            return "redirect:/auth/login";
        }

        if (customerInfo != null && size != null){
            System.out.println(productCode);
            ShoppingCart cart = shoppingCartService.findByEmail(customerInfo.getEmail());
            String cartCode = cart.getCartCode();
            System.out.println("success");
            cartItemService.addToCart(cartCode,productCode,quantity,size,price*quantity);
            System.out.println(productCode + quantity + size + price*quantity + cartCode);

        }else{
            System.out.println("error");
            model.addAttribute("checkSession", "");
        }

        return "redirect:" + request.getHeader("Referer");
    }



}
