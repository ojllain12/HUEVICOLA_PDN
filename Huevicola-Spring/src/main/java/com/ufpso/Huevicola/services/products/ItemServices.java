package com.ufpso.Huevicola.services.products;

import com.ufpso.Huevicola.models.entities.products.Item;

import java.util.List;
import java.util.Optional;

public interface ItemServices {
    List<Item> findAll();

    Optional<Item> findById(Long id);

    Item save(Item item);

    Optional<Item> update(Long id, Item item);

    boolean delete(Long id);
}
