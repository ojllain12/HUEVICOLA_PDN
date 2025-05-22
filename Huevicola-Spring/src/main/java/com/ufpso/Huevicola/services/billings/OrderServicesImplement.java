package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.Order;
import com.ufpso.Huevicola.repository.billings.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServicesImplement implements OrderServices {
    private final OrderRepository repository;

    @Autowired
    public OrderServicesImplement(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Order> getAll() {
        return (List<Order>) repository.findAll();
    }

    @Override
    public Optional<Order> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return repository.save(order);
    }

    @Override
    public Optional<Order> update(Long id, Order updated) {
        return repository.findById(id).map(existing -> {
            existing.setDate(updated.getDate());
            existing.setSubtotal(updated.getSubtotal());
            existing.setDiscount(updated.getDiscount());
            existing.setTotal(updated.getTotal());
            existing.setDelivered(updated.isDelivered());
            existing.setDeliveredDate(updated.getDeliveredDate());
            existing.setPaid(updated.isPaid());
            existing.setPaidDate(updated.getPaidDate());
            existing.setCustomer(updated.getCustomer());
            existing.setPaymentMethod(updated.getPaymentMethod());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id).map(entity -> {
            repository.delete(entity);
            return true;
        }).orElse(false);
    }
}
