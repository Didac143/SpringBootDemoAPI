package com.example.springbootdemoapi.service;

import com.example.springbootdemoapi.model.Cart;
import com.example.springbootdemoapi.model.vo.CartItemVO;

public interface CartService {
    Cart addToNewCart(CartItemVO cartItemVO);
    Cart addToExistingCart(Long cartId, CartItemVO cartItemVO);
    Cart removeCartItem(Long cartId, Long reservationId);
    Cart removeCart(Long cartId);
    Cart getCartById(Long cartId);
}
