package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerServices {
    Customer create(Customer customer);

    List<Customer> getAll();

    Optional<Customer> getById(Long id);

    Optional<Customer> update(Long id, Customer customer);

    void delete(Long id);
}
