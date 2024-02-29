package com.linda.projet.projet.dto;


import com.linda.projet.projet.models.Cart;
import com.linda.projet.projet.models.Invoice;
import com.linda.projet.projet.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class InvoiceDto {

    private Integer id;
    private LocalDateTime invoiceDate;
    private Double amount;
    private Integer userId;

    public static InvoiceDto fromEntity(Invoice invoice) {
        return InvoiceDto.builder()
                .id(invoice.getId())
                .invoiceDate(invoice.getInvoiceDate())
                .amount(invoice.getAmount())
                .userId(invoice.getUser().getId())
                .build();
    }


    public static Invoice toEntity(InvoiceDto invoiceDto) {
        return Invoice.builder()
                .id(invoiceDto.getId())
                .invoiceDate(invoiceDto.getInvoiceDate())
                .amount(invoiceDto.getAmount())
                .user(User.builder().id(invoiceDto.getUserId()).build())
                .build();
    }



}
