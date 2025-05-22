package com.ufpso.Huevicola.services.products;

import com.ufpso.Huevicola.models.entities.products.Type;
import com.ufpso.Huevicola.repository.products.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServicesImplement implements TypeServices {
    private final TypeRepository repository;

    @Autowired
    public TypeServicesImplement(TypeRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Type> findAll() {
        return (List<Type>) repository.findAll();
    }

    @Override
    public Optional<Type> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Type save(Type type) {
        return repository.save(type);
    }

    @Override
    public Optional<Type> update(Long id, Type type) {
        return repository.findById(id).map(existing -> {
            existing.setName(type.getName());
            existing.setCategory(type.getCategory());
            return repository.save(existing);
        });
    }

    @Override
    public boolean delete(Long id) {
        return repository.findById(id).map(type -> {
            repository.delete(type);
            return true;
        }).orElse(false);
    }
}
