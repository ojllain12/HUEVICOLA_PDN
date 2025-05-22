package com.ufpso.Huevicola.repository.products;

import com.ufpso.Huevicola.models.entities.products.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
