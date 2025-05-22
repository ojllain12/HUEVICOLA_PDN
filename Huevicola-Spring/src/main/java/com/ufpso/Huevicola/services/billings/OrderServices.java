package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.Order;

import java.util.List;
import java.util.Optional;

public interface OrderServices {
    List<Order> getAll();

    Optional<Order> getById(Long id);

    Order save(Order order);

    Optional<Order> update(Long id, Order order);

    boolean delete(Long id);
}
