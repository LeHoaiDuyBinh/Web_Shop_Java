package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.ProductImage;
import com.example.web_shop_ptit.client.entity.ProductSizes;
import com.example.web_shop_ptit.client.repository.ProductImgRepository;
import com.example.web_shop_ptit.client.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSizeService {
    @Autowired
    private ProductSizeRepository repo;

    public List<ProductSizes> listAll(){
        return repo.findAll();
    }

    public List<ProductSizes> getProductSizesByCode(String productCode) {
        return repo.findByProductCode(productCode);
    }
}
