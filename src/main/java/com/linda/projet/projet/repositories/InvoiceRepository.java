package com.linda.projet.projet.repositories;

import com.linda.projet.projet.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
