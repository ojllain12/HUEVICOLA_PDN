package com.ufpso.Huevicola.services.products;

import com.ufpso.Huevicola.models.entities.products.Item;
import com.ufpso.Huevicola.repository.products.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServicesImplement implements ItemServices {
    private final ItemRepository repository;

    @Autowired
    public ItemServicesImplement(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Item> findAll() {
        return (List<Item>) repository.findAll();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return repository.save(item);
    }

    @Override
    public Optional<Item> update(Long id, Item item) {
        return repository.findById(id).map(existing -> {
            existing.setName(item.getName());
            existing.setStock(item.getStock());
            existing.setPrice(item.getPrice());
            existing.setType(item.getType());
            existing.setUser(item.getUser());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id).map(item -> {
            repository.delete(item);
            return true;
        }).orElse(false);
    }
}
