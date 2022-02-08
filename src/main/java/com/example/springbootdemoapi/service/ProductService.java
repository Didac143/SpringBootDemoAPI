package com.example.springbootdemoapi.service;

import com.example.springbootdemoapi.model.Product;
import com.example.springbootdemoapi.model.vo.ProductVO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product addProduct(ProductVO productVO);
    List<Product> getProducts(Map<String, String> params);
    Product getProductById(Long productId);
    Product updateProduct(Long productId, ProductVO productVO);
    Product deleteProduct(Long productId);
}
