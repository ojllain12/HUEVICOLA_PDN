package com.ufpso.Huevicola.repository.billings;

import com.ufpso.Huevicola.models.entities.billings.Customer;
import com.ufpso.Huevicola.models.entities.billings.DetailProducts;
import org.springframework.data.repository.CrudRepository;

public interface DetailProductsRepository extends CrudRepository<DetailProducts, Long> {
}
