package com.ufpso.Huevicola.repository.billings;

import com.ufpso.Huevicola.models.entities.billings.Customer;
import com.ufpso.Huevicola.models.entities.billings.PaymentMethod;
import org.springframework.data.repository.CrudRepository;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {
}
