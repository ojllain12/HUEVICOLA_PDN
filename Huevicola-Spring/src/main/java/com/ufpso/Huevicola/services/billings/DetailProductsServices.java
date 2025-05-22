package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.DetailProducts;

import java.util.List;
import java.util.Optional;

public interface DetailProductsServices {
    List<DetailProducts> getAll();

    Optional<DetailProducts> getById(Long id);

    DetailProducts save(DetailProducts detail);

    Optional<DetailProducts> update(Long id, DetailProducts detail);

    boolean delete(Long id);
}
