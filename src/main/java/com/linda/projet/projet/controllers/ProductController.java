package com.linda.projet.projet.controllers;


import com.linda.projet.projet.dto.ProductDto;
import com.linda.projet.projet.dto.UserDto;
import com.linda.projet.projet.models.User;
import com.linda.projet.projet.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Integer> save(@RequestBody ProductDto productDto) {
        return ResponseEntity.ok(service.save(productDto));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/products/{product-id}")
    public ResponseEntity<ProductDto> findById(@PathVariable("product-id") Integer productId) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @DeleteMapping("/products/{product-id}")
    public ResponseEntity<Void> delete(@PathVariable("product-id") Integer productId) {
        service.delete(productId);
        return ResponseEntity.accepted().build();
    }



}
