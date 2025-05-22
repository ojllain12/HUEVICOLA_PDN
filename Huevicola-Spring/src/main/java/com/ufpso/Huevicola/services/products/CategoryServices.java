package com.ufpso.Huevicola.services.products;

import com.ufpso.Huevicola.models.entities.products.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryServices {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);

    Optional<Category> update(Long id, Category category);

    boolean delete(Long id);
}
