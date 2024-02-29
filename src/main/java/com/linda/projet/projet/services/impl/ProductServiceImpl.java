package com.linda.projet.projet.services.impl;

import com.linda.projet.projet.dto.ProductDto;
import com.linda.projet.projet.models.Product;
import com.linda.projet.projet.repositories.ProductRepository;
import com.linda.projet.projet.services.ProductService;
import com.linda.projet.projet.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {




    private final ProductRepository repository;
    private final ObjectsValidator<ProductDto> validator;



    @Override
    public Product getProductById(Integer productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public Integer save(ProductDto dto) {
        validator.validate(dto);
        Product product = ProductDto.toEntity(dto);
        return repository.save(product).getId();
    }

    @Override
    public List<ProductDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(Integer id) {
        return repository.findById(id)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(" was not found "));
    }

    @Override
    public void delete(Integer id) {

        repository.deleteById(id);

    }


  /*  @Override
    public void decrementStock(Integer productId, Integer quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
        product.setQuantity(product.getQuantity() - quantity);
        repository.save(product);
    }

    @Override
    public void incrementStock(Integer productId, Integer quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
    }*/



}
