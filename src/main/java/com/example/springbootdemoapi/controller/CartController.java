package com.example.springbootdemoapi.controller;

import com.example.springbootdemoapi.model.Cart;
import com.example.springbootdemoapi.model.vo.CartItemVO;
import com.example.springbootdemoapi.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Object> createCart(@RequestBody CartItemVO cartItemVO) {
        return new ResponseEntity<>(cartService.addToNewCart(cartItemVO), HttpStatus.CREATED);
    }

    @PostMapping("{cartId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId, @RequestBody CartItemVO cartItemVO) {
        return new ResponseEntity<>(cartService.addToExistingCart(cartId, cartItemVO), HttpStatus.CREATED);
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<Cart> deleteCart(@PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.removeCart(cartId), HttpStatus.OK);
    }

    @DeleteMapping("{cartId}/items/{cartItemId}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable Long cartId, @PathVariable Long cartItemId) {
        return new ResponseEntity<>(cartService.removeCartItem(cartId, cartItemId), HttpStatus.OK);
    }

}
