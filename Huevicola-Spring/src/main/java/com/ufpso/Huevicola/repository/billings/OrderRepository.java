package com.ufpso.Huevicola.repository.billings;

import com.ufpso.Huevicola.models.entities.billings.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
