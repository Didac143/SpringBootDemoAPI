package com.example.springbootdemoapi.controller;

import com.example.springbootdemoapi.model.Product;
import com.example.springbootdemoapi.model.vo.ProductVO;
import com.example.springbootdemoapi.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductVO productVO) {
        return new ResponseEntity<>(productService.addProduct(productVO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(productService.getProducts(params), HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PatchMapping("{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId,
                                                 @RequestBody ProductVO productVO) {
        return new ResponseEntity<>(productService.updateProduct(productId, productVO), HttpStatus.OK);
    }

    @DeleteMapping("{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }

}
