package com.ufpso.Huevicola.repository.products;

import com.ufpso.Huevicola.models.entities.products.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
}
