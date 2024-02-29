package com.linda.projet.projet.dto;


import com.linda.projet.projet.models.Cart;
import com.linda.projet.projet.models.Product;
import com.linda.projet.projet.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class CartDto {

    private Integer id;
    private Integer userId;
    private List<ProductDto> products;


    public static CartDto fromEntity(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .products(cart.getProducts().stream().map(ProductDto::fromEntity).collect(Collectors.toList()))
                .build();
    }


    public static Cart toEntity(CartDto cartDto) {
        Cart cart = Cart.builder()
                .id(cartDto.getId())
                .user(User.builder().id(cartDto.getUserId()).build())
                .build();

        List<Product> products = cartDto.getProducts().stream()
                .map(productDto -> {
                    Product product = Product.builder()
                            .id(productDto.getId())
                            .name(productDto.getName())
                            .price(productDto.getPrice())
                            .quantity(productDto.getQuantity())  // Utilisez la quantité du DTO
                            .build();
                    product.setCarts(Collections.singletonList(cart));  // Associez ce produit à ce panier uniquement
                    return product;
                })
                .collect(Collectors.toList());

        cart.setProducts(products);
        return cart;

    }


}
