package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.*;
import com.example.web_shop_ptit.client.service.CategoryService;
import com.example.web_shop_ptit.client.service.ProductImgService;
import com.example.web_shop_ptit.client.service.ProductService;
import com.example.web_shop_ptit.client.service.ProductSizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Category> listCategory = categoryService.listAll();
        for (Category category : listCategory) {
            if(product.getCategory().getCategoryId() == category.getCategoryId()) {
                model.addAttribute("category", category);
            }
        }

        List<ProductImage> productImages = productImgService.listAll();
        List<ProductImage> listImg = new ArrayList<>();
        for (ProductImage productImage : productImages) {
            if(productImage.getId().getProductCode().equals(productCode)){
                listImg.add(productImage);
            }
        }

        List<Product> similarProducts = service.getSimilarProducts(product.getCategory().getCategoryId(), productCode);
        List<ProductImage> ImgSimilarProducts = new ArrayList<>();

        int counter = 0;
        for(Product similarProduct : similarProducts){
            for(ProductImage ImgSimilar : productImages){
                if(ImgSimilar.getId().getProductCode().equals(similarProduct.getProductCode())){
                    if (counter % 4 == 0) {
                        ImgSimilarProducts.add(ImgSimilar);
                    }
                    counter++;
                }
            }
        }

        String nameParentCategory = "";
        Integer parentId = null;
        for(Category Category : listCategory){
            if(product.getCategory().getCategoryId() == Category.getCategoryId()){
                parentId = Category.getParentCategoryId();
                for(Category Category1 : listCategory){
                    if(parentId == Category1.getCategoryId()){
                        nameParentCategory = Category1.getName();
                    }
                }
            }
        }

        if(similarProducts.size() > 0){
            model.addAttribute("similarProducts",similarProducts);
        }

        List<ProductSizes> productSizes = productSizeService.findByProductCode(productCode);

        for(ProductSizes productSizes1 : productSizes){
            System.out.println(productSizes1.getId().getProductCode());
            System.out.println(productSizes1.getId().getSize());
            System.out.println(productSizes1.getQuantity());
        }

        model.addAttribute("nameParentCategory",nameParentCategory);
        model.addAttribute("productImages",productImages);
        model.addAttribute("product", product);
        model.addAttribute("listImg", listImg);
        model.addAttribute("ImgSimilarProducts", ImgSimilarProducts);
        model.addAttribute("productSizes",productSizes);
        return "web_client/product";
    }

    @GetMapping("/category")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<Product> productList = service.findByNameContaining(keyword);

        for(Product product: productList){
            System.out.println(product.getName());
            System.out.println(product.getPrice());
        }
        System.out.println(productList.size());

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

}
