package com.example.web_shop_ptit.client.service;

import com.example.web_shop_ptit.client.entity.Product;

import java.util.List;

public interface ProductServiceInterface {
    List<Product> getSimilarProducts(long categoryId, String productCode);

}
