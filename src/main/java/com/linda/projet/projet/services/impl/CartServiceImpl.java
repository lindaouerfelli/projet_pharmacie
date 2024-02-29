package com.linda.projet.projet.services.impl;

import com.linda.projet.projet.dto.CartDto;
import com.linda.projet.projet.dto.ProductDto;
import com.linda.projet.projet.models.Cart;
import com.linda.projet.projet.models.Product;
import com.linda.projet.projet.models.User;
import com.linda.projet.projet.repositories.CartRepository;
import com.linda.projet.projet.repositories.ProductRepository;
import com.linda.projet.projet.repositories.UserRepository;
import com.linda.projet.projet.services.CartService;
import com.linda.projet.projet.services.ProductService;
import com.linda.projet.projet.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserRepository userRepository;


    @Override
    public Cart findById(Integer id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
    }

    @Override
    @Transactional

    public void addProductToCart(Cart cart, Product product) {

        cart.addProduct(product);
        cartRepository.save(cart);
        product.setQuantity(product.getQuantity() - 1);

    }
    @Override
    public Cart findByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }
    @Override
    @Transactional
    public void removeProductFromCart(Cart cart, Product product) {

        cart.removeProduct(product);
        cartRepository.save(cart);
        product.setQuantity(product.getQuantity() + 1);

    }

    @Override
    @Transactional
    public Cart createCart(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    @Override
    @Transactional
    public Cart getCartByUser(User user) {
        return cartRepository.findByUser(user);

    }
}
