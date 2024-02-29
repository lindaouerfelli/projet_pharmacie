package com.linda.projet.projet.services;

import com.linda.projet.projet.dto.CartDto;
import com.linda.projet.projet.models.Cart;
import com.linda.projet.projet.models.Product;
import com.linda.projet.projet.models.User;

import java.util.List;

public interface CartService{
    Cart findById(Integer id);
    Cart findByUserId(Integer userId);
    void addProductToCart(Cart cart, Product product);
    void removeProductFromCart(Cart cart, Product product);
    Cart createCart(User user);
    Cart getCartByUser(User user);
}
