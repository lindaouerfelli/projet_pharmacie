package com.linda.projet.projet.services;

import com.linda.projet.projet.dto.ProductDto;
import com.linda.projet.projet.models.Product;

public interface ProductService extends AbstractService<ProductDto> {

    Product getProductById(Integer productId);


    //void decrementStock(Integer productId, Integer quantity);

    //void incrementStock(Integer productId, Integer quantity);
}

