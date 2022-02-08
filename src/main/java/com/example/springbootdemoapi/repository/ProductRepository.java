package com.example.springbootdemoapi.repository;

import com.example.springbootdemoapi.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "(:clientId is null or p.clientId = :clientId) and " +
            "(:priceGreater is null or p.price > :priceGreater) and " +
            "(:priceLess is null or p.price < :priceLess)")
    List<Product> findFilteredProducts(@Param("clientId") Long clientId,
                                       @Param("priceGreater") BigDecimal priceGreater,
                                       @Param("priceLess") BigDecimal priceLess);

    List<Product> findProductsByPriceLessThan(BigDecimal price);
}
