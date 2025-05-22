package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.PaymentMethod;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodServices {
    List<PaymentMethod> getAll();

    Optional<PaymentMethod> getById(Long id);

    Optional<PaymentMethod> create(PaymentMethod paymentMethod);

    Optional<PaymentMethod> update(Long id, PaymentMethod updatedPaymentMethod);

    void delete(Long id);
}
