package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Product;
import com.example.web_shop_ptit.client.entity.ProductImage;
import com.example.web_shop_ptit.client.repository.ProductImgRepository;
import com.example.web_shop_ptit.client.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImgService {
    @Autowired
    private ProductImgRepository repo;

    public List<ProductImage> listAll(){
        return repo.findAll();
    }


}
