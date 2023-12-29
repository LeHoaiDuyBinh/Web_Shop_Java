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

    public void saveProduct(String MaSanPham, String TenSanPham, Long GiaSanPham, String color, Long SoLuongSP_S,
                            Long SoLuongSP_M, Long SoLuongSP_L, Long SoLuongSP_XL, Long SoLuongSP_XXL,
                            String MoTa, List<String> images, CategoryManagement DanhMuc) {
        try {
            ProductManagement product = new ProductManagement();
            product.setProductCode(MaSanPham);
            product.setName(TenSanPham);
            product.setPrice(GiaSanPham);
            product.setDescription(MoTa);
            product.setCategory(DanhMuc);

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

    public void updateProduct(String MaSanPham, String TenSanPham, Long GiaSanPham, String color, Long SoLuongSP_S,
                            Long SoLuongSP_M, Long SoLuongSP_L, Long SoLuongSP_XL, Long SoLuongSP_XXL,
                            String MoTa, List<String> images, CategoryManagement DanhMuc) {

        try {
            ProductManagement product = repo.findByProductCode(MaSanPham);
            product.setName(TenSanPham);
            product.setPrice(GiaSanPham);
            product.setColor(color);
            product.setDescription(MoTa);
            product.setCategory(DanhMuc);

            List<ProductImageManagement> listProductImage = product.getProductImages();

            List<String> ordinals = new ArrayList<>();
            ordinals.add("first");
            ordinals.add("second");
            ordinals.add("third");
            ordinals.add("fourth");

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

            List<ProductSizeManagement> listProductSize = product.getProductSizes();

            for (int i = 0; i < listProductSize.size(); i++) {
                ProductSizeManagement productSize = listProductSize.get(i);
                productSize.setQuantity(map.get(productSize.getId().getSize()));
            }

            repo.save(product);
        }
        catch (Exception e){
            throw new EditProductException("Đã xảy ra lỗi trong quá trình sửa sản phẩm.");
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
