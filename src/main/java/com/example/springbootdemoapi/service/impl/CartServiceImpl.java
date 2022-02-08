package com.example.springbootdemoapi.service.impl;

import com.example.springbootdemoapi.model.Cart;
import com.example.springbootdemoapi.model.Reservation;
import com.example.springbootdemoapi.model.exception.RequestException;
import com.example.springbootdemoapi.model.vo.CartItemVO;
import com.example.springbootdemoapi.model.vo.ReservationVO;
import com.example.springbootdemoapi.repository.CartRepository;
import com.example.springbootdemoapi.service.CartService;
import com.example.springbootdemoapi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ReservationService reservationService;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,
                           ReservationService reservationService) {
        this.cartRepository = cartRepository;
        this.reservationService = reservationService;
    }

    public Cart addToNewCart(CartItemVO cartItemVO) {
        Cart cart = new Cart();
        cartRepository.save(cart);

        cart.addItem(reservationService.getReservationById(cartItemVO.getReservationId()));
        System.out.println(cart.getReservations());

        return cartRepository.save(cart);
    }

    public Cart addToExistingCart(Long cartId, CartItemVO cartItemVO) {
        Cart cart = getCartById(cartId);
        Reservation reservation = reservationService.getReservationById(cartItemVO.getReservationId());

        if (cart.getReservations().contains(reservation)) {
            ReservationVO reservationVO = new ReservationVO();
            reservationVO.setQuantity(reservation.getQuantity()+1);
            reservationService.updateReservation(cartItemVO.getReservationId(), reservationVO);
        } else {
            cart.addItem(reservation);
        }

        return cartRepository.save(cart);
    }

    public Cart removeCartItem(Long cartId, Long reservationId) {
        Cart cart = getCartById(cartId);
        Reservation reservation = reservationService.getReservationById(reservationId);

        cart.removeItem(reservation);

        return cartRepository.save(cart);
    }

    public Cart removeCart(Long cartId) {
        Cart deletedCart = getCartById(cartId);
        cartRepository.delete(deletedCart);
        return deletedCart;
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RequestException("Cart not found"));
    }

}
