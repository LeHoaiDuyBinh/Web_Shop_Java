package com.example.web_shop_ptit.client.repository;

import com.example.web_shop_ptit.client.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImage, ProductImage.ProductImageId> {
//    List<ProductImage> findByProductCode(String productCode);

}
