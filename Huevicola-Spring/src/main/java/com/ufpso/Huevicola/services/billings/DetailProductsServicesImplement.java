package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.DetailProducts;
import com.ufpso.Huevicola.repository.billings.DetailProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetailProductsServicesImplement implements DetailProductsServices {
    private final DetailProductsRepository repository;

    @Autowired
    public DetailProductsServicesImplement(DetailProductsRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<DetailProducts> getAll() {
        return (List<DetailProducts>) repository.findAll();
    }

    @Override
    public Optional<DetailProducts> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public DetailProducts save(DetailProducts detail) {
        return repository.save(detail);
    }

    @Override
    public Optional<DetailProducts> update(Long id, DetailProducts updated) {
        return repository.findById(id).map(existing -> {
            existing.setCant(updated.getCant());
            existing.setPriceUnit(updated.getPriceUnit());
            existing.setPriceTotal(updated.getPriceTotal());
            existing.setItem(updated.getItem());
            existing.setOrder(updated.getOrder());
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
