package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.Customer;
import com.ufpso.Huevicola.repository.billings.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServicesImplement implements CustomerServices {
    private final CustomerRepository repository;

    @Autowired
    public CustomerServicesImplement(CustomerRepository repository) {
        this.repository = repository;
    }


    @Override
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return (List<Customer>) repository.findAll();
    }

    @Override
    public Optional<Customer> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Customer> update(Long id, Customer customer) {
        return repository.findById(id).map(existing -> {
            existing.setName(customer.getName());
            existing.setAddress(customer.getAddress());
            existing.setPhone(customer.getPhone());
            existing.setDni(customer.getDni());
            existing.setDiscount(customer.getDiscount());
            return repository.save(existing);
        });
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
