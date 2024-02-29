package com.linda.projet.projet.services.impl;

import com.linda.projet.projet.dto.InvoiceDto;
import com.linda.projet.projet.dto.ProductDto;
import com.linda.projet.projet.models.Invoice;
import com.linda.projet.projet.models.Product;
import com.linda.projet.projet.repositories.InvoiceRepository;
import com.linda.projet.projet.repositories.ProductRepository;
import com.linda.projet.projet.services.InvoiceService;
import com.linda.projet.projet.validators.ObjectsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;
    private final ObjectsValidator<InvoiceDto> validator;

    @Override
    public Integer save(InvoiceDto dto) {
        validator.validate(dto);
        Invoice invoice = InvoiceDto.toEntity(dto);
        return repository.save(invoice).getId();
    }

    @Override
    public List<InvoiceDto> findAll() {
        return repository.findAll()
                .stream()
                .map(InvoiceDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDto findById(Integer id) {
        return repository.findById(id)
                .map(InvoiceDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(" user not found "));
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);


    }
}
