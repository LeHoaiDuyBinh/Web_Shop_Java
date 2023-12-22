package com.example.web_shop_ptit.client.controller;

import com.example.web_shop_ptit.client.entity.Category;
import com.example.web_shop_ptit.client.entity.Product;
import com.example.web_shop_ptit.client.entity.ProductImage;
import com.example.web_shop_ptit.client.service.CategoryService;
import com.example.web_shop_ptit.client.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/auth/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImgService productImgService;

    @GetMapping("/category/{slug}")
    public String showCategoryPage(@PathVariable String slug, Model model) {
        List<Category> listCategory = categoryService.listAll();
        for (Category category : listCategory) {
            if(slug.equals(removeDiacritics(category.getName()))){
                model.addAttribute("productCategorys", category);
            }
        }
        List<ProductImage> listProductImg = productImgService.listAll();
        List<ProductImage> listImg = new ArrayList<>();
        int flag = 0;
        for ( ProductImage productImage : listProductImg) {
            flag++;
            if(flag == 4){
                listImg.add(productImage);
                flag = 0;
            }
        }
        System.out.println(listImg.size());
        model.addAttribute("listProductImgs", listImg);
        model.addAttribute("listCategorys", listCategory);
        return "web_client/category";
    }

    private String removeDiacritics(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return createFormat(pattern.matcher(normalized).replaceAll(""));
    }

    private String createFormat(String input) {
        return input.toLowerCase().replaceAll("\\s", "-");
    }
}
