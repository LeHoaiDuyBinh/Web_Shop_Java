package com.example.web_shop_ptit.admin.service;

import com.example.web_shop_ptit.admin.entity.CategoryManagement;
import com.example.web_shop_ptit.admin.entity.ProductImageManagement;
import com.example.web_shop_ptit.admin.entity.ProductManagement;
import com.example.web_shop_ptit.admin.entity.ProductSizeManagement;
import com.example.web_shop_ptit.admin.exception.AddProductException;
import com.example.web_shop_ptit.admin.exception.DeleteProductException;
import com.example.web_shop_ptit.admin.exception.EditProductException;
import com.example.web_shop_ptit.admin.repository.ProductManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductManagementService {
    @Autowired
    private ProductManagementRepository repo;

    public List<ProductManagement> listAll(){return (List<ProductManagement>) repo.findAll();}
    public ProductManagement getProductByProductCode(String productCode){return repo.findByProductCode(productCode);}

    public void saveProduct(ProductManagement product, Long price, String MaSanPham, Long SoLuongSP_S,
                            Long SoLuongSP_M, Long SoLuongSP_L, Long SoLuongSP_XL, Long SoLuongSP_XXL,
                            List<String> images, CategoryManagement DanhMuc) {
        try {
            product.setProductCode(MaSanPham);
            product.setCategory(DanhMuc);
            product.setPrice(price);
            product.setUpdateLatest(new Date());
            List<ProductImageManagement> listProductImage = new ArrayList<>();

            List<String> ordinals = new ArrayList<>();
            ordinals.add("first");
            ordinals.add("second");
            ordinals.add("third");
            ordinals.add("fourth");

            for (int i = 0; i < images.size(); i++) {
                ProductImageManagement productImage = new ProductImageManagement();
                ProductImageManagement.ProductImageManagementId productImageId = new ProductImageManagement.ProductImageManagementId();
                productImageId.setProduct(product);
                productImageId.setOrdinalNumber(ordinals.get(i));
                productImage.setId(productImageId);
                productImage.setImage(images.get(i));
                listProductImage.add(productImage);
            }

            product.setProductImages(listProductImage);

            Map<String, Long> map = new HashMap<>();
            map.put("S", SoLuongSP_S);
            map.put("M", SoLuongSP_M);
            map.put("L", SoLuongSP_L);
            map.put("XL", SoLuongSP_XL);
            map.put("XXL", SoLuongSP_XXL);

            List<ProductSizeManagement> listProductSize = new ArrayList<>();

            for (Map.Entry<String, Long> entry : map.entrySet()) {
                ProductSizeManagement productSize = new ProductSizeManagement();
                ProductSizeManagement.ProductSizeManagementId productSizeId = new ProductSizeManagement.ProductSizeManagementId();
                productSizeId.setProduct(product);
                productSizeId.setSize(entry.getKey());
                productSize.setId(productSizeId);
                productSize.setQuantity(entry.getValue());
                listProductSize.add(productSize);
            }

            product.setProductSizes(listProductSize);

            repo.save(product);
        }
        catch (Exception e){
            throw new AddProductException("Đã xảy ra lỗi trong quá trình thêm sản phẩm.");
        }
    }

    public void updateProduct(ProductManagement product, Long price, Long SoLuongSP_S,
                              Long SoLuongSP_M, Long SoLuongSP_L, Long SoLuongSP_XL, Long SoLuongSP_XXL,
                              List<String> images, CategoryManagement DanhMuc) {

        try {
            ProductManagement productOld = repo.findByProductCode(product.getProductCode());
            productOld.setPrice(price);
            productOld.setCategory(DanhMuc);
            productOld.setUpdateLatest(new Date());
            productOld.setName(product.getName());
            productOld.setColor(product.getColor());
            productOld.setDescription(product.getDescription());

            List<ProductImageManagement> listProductImage = productOld.getProductImages();

            for (int i = 0; i < listProductImage.size(); i++) {
                ProductImageManagement productImage = listProductImage.get(i);
                productImage.setImage(images.get(i));
            }

            Map<String, Long> map = new HashMap<>();
            map.put("S", SoLuongSP_S);
            map.put("M", SoLuongSP_M);
            map.put("L", SoLuongSP_L);
            map.put("XL", SoLuongSP_XL);
            map.put("XXL", SoLuongSP_XXL);

            List<ProductSizeManagement> listProductSize = productOld.getProductSizes();

            for (int i = 0; i < listProductSize.size(); i++) {
                ProductSizeManagement productSize = listProductSize.get(i);
                productSize.setQuantity(map.get(productSize.getId().getSize()));
            }

            repo.save(productOld);
        }
        catch (Exception e){
            throw new EditProductException("Đã xảy ra lỗi trong quá trình sửa sản phẩm.");
//            throw new EditProductException(e.getMessage());
        }
    }

    public void deleteProduct(String productCode) {
        try {
            repo.deleteById(productCode);
        }
        catch (Exception e){
            throw new DeleteProductException("Đã xảy ra lỗi trong quá trình xóa sản phẩm.");
        }
    }
}
