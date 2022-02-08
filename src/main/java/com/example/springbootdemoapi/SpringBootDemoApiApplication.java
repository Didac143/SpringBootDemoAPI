package com.example.springbootdemoapi;

import com.example.springbootdemoapi.model.vo.ClientVO;
import com.example.springbootdemoapi.model.vo.ProductVO;
import com.example.springbootdemoapi.service.impl.ClientServiceImpl;
import com.example.springbootdemoapi.service.impl.ProductServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class SpringBootDemoApiApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringBootDemoApiApplication.class, args);

        ClientServiceImpl cs = ctx.getBean(ClientServiceImpl.class);
        ProductServiceImpl ps = ctx.getBean(ProductServiceImpl.class);

        cs.addClient(new ClientVO("Didac"));
        cs.addClient(new ClientVO("Marina"));

        ps.addProduct(new ProductVO("Mesa", BigDecimal.valueOf(29.99), 1L));
        ps.addProduct(new ProductVO("Puerta", BigDecimal.valueOf(39.99), 1L));
        ps.addProduct(new ProductVO("Silla", BigDecimal.valueOf(19.99), 2L));
    }

}
