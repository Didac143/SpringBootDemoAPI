package com.example.springbootdemoapi.service.impl;

import com.example.springbootdemoapi.model.Product;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.ProductVO;
import com.example.springbootdemoapi.repository.ProductRepository;
import com.example.springbootdemoapi.service.ClientService;
import com.example.springbootdemoapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ClientService clientService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ClientService clientService) {
        this.productRepository = productRepository;
        this.clientService = clientService;
    }

    public Product addProduct(ProductVO productVO) {
        Product product = new Product();
        product.setName(productVO.getName());
        product.setPrice(productVO.getPrice());
        product.setClientId(productVO.getClientId());
        return productRepository.save(product);
    }

    public List<Product> getProducts(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params)) return (List<Product>)productRepository.findAll();

        BigDecimal priceGreater = null, priceLess = null;
        Long clientId = null;

        try {
            if (params.containsKey("clientId")) {
                clientId = Long.valueOf(params.get("clientId"));
                clientService.getClientById(clientId);
            }
            if (params.containsKey("priceGreater")) priceGreater = new BigDecimal(params.get("priceGreater"));
            if (params.containsKey("priceLess")) priceLess = new BigDecimal(params.get("priceLess"));
        } catch (NumberFormatException e)  {
            throw new RequestException(e.getMessage());
        }

        return productRepository.findFilteredProducts(clientId, priceGreater, priceLess);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new RequestException("Product not found"));
    }

    public Product updateProduct(Long productId, ProductVO productVO) {
        Product product = getProductById(productId);
        if (productVO.getName() != null) {
            product.setName(productVO.getName());
        }
        if (productVO.getPrice() != null) {
            product.setPrice(productVO.getPrice());
        }
        if (productVO.getClientId() != null) {
            product.setClientId(productVO.getClientId());
        }
        return productRepository.save(product);
    }

    public Product deleteProduct(Long productId){
        Product deleted = getProductById(productId);
        productRepository.delete(deleted);
        return deleted;
    }

}
