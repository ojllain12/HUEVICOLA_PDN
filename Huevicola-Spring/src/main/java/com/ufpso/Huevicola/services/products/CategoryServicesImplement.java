package com.ufpso.Huevicola.services.products;

import com.ufpso.Huevicola.models.entities.products.Category;
import com.ufpso.Huevicola.repository.products.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServicesImplement implements CategoryServices {
    private final CategoryRepository repository;

    @Autowired
    public CategoryServicesImplement(CategoryRepository repository) {
        this.repository = repository;
    }



    @Override
    public List<Category> findAll() {
        return (List<Category>) repository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Optional<Category> update(Long id, Category category) {
        return repository.findById(id).map(existing -> {
            existing.setName(category.getName());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id).map(cat -> {
            repository.delete(cat);
            return true;
        }).orElse(false);
    }
}
