package com.ufpso.Huevicola.services.products;

import com.ufpso.Huevicola.models.entities.products.Type;

import java.util.List;
import java.util.Optional;

public interface TypeServices {
    List<Type> findAll();

    Optional<Type> findById(Long id);

    Type save(Type type);

    Optional<Type> update(Long id, Type type);

    boolean delete(Long id);
}
