package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Product;
import com.example.web_shop_ptit.client.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }
}
