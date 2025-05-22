package com.ufpso.Huevicola.repository.billings;

import com.ufpso.Huevicola.models.entities.billings.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
