package com.linda.projet.projet.controllers;

import com.linda.projet.projet.dto.CartDto;
import com.linda.projet.projet.dto.InvoiceDto;
import com.linda.projet.projet.dto.ProductDto;
import com.linda.projet.projet.dto.UserDto;
import com.linda.projet.projet.models.Cart;
import com.linda.projet.projet.models.Invoice;
import com.linda.projet.projet.models.Product;
import com.linda.projet.projet.models.User;
import com.linda.projet.projet.repositories.CartRepository;
import com.linda.projet.projet.repositories.InvoiceRepository;
import com.linda.projet.projet.services.CartService;
import com.linda.projet.projet.services.InvoiceService;
import com.linda.projet.projet.services.ProductService;
import com.linda.projet.projet.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor

public class CartController {


    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final UserService userService;
    private final InvoiceService invoiceService;
    private final InvoiceRepository invoiceRepository;



    @PostMapping("/{userId}/add/{productId}")
    public void addProductToCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            Cart cart = cartService.getCartByUser(user);
            if (cart == null) {
                cart = cartService.createCart(user);
            }
            Product product = productService.getProductById(productId);
            if (product != null) {
                cartService.addProductToCart(cart, product);
            } else {
                System.out.println("PRODUCT IS NULL");

            }
        } else {
            System.out.println("USER IS NULL");
        }
    }

    @PostMapping("/{userId}/remove/{productId}")
    public void removeProductFromCart(@PathVariable Integer userId, @PathVariable Integer productId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            Cart cart = cartService.getCartByUser(user);
            if (cart != null) {
                Product product = productService.getProductById(productId);
                if (product != null) {
                    cartService.removeProductFromCart(cart, product);
                } else {
                    System.out.println("PRODUCT IS NULL");
                }
            } else {
                System.out.println("CART IS NULL");
            }
        } else {
            System.out.println("USER IS NULL");
        }
    }


    @GetMapping("/{cartId}/products")
    public ResponseEntity<CartDto> getCartProducts(@PathVariable("cartId") Integer cartId) {
        Cart cart = cartService.findById(cartId);
        if (cart == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Convertir l'objet Cart en CartDto
        CartDto cartDto = CartDto.fromEntity(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


    @GetMapping("/invoice/{user-id}")
    public ResponseEntity<InvoiceDto> generateInvoice(@PathVariable("user-id") Integer userId) {
        try {
            // Récupérer le panier de l'utilisateur
            Cart cart = cartService.findByUserId(userId);

            Double totalAmount = calculateTotalAmount(cart.getProducts());

            Invoice invoice = new Invoice();
            invoice.setAmount(totalAmount);
            invoice.setInvoiceDate(LocalDateTime.now());
            invoice.setUser(cart.getUser());

            // Enregistrer la facture dans la base de données
            Invoice savedInvoice = invoiceRepository.save(invoice);
            InvoiceDto invoiceDto = InvoiceDto.fromEntity(savedInvoice);

            return ResponseEntity.ok(invoiceDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private double calculateTotalAmount(List<Product> products) {
        // Initialiser le montant total à zéro
        double totalAmount = 0.0;

        for (Product product : products) {
            double productPrice = product.getPrice();
            totalAmount += productPrice;
        }

        // Retourner le montant total
        return totalAmount;
    }



}
