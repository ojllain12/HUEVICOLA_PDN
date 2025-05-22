package com.ufpso.Huevicola.services.billings;

import com.ufpso.Huevicola.models.entities.billings.PaymentMethod;
import com.ufpso.Huevicola.repository.billings.PaymentMethodRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodServicesImplement implements PaymentMethodServices {
    private final PaymentMethodRepository repository;

    @Autowired
    public PaymentMethodServicesImplement(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    public List<PaymentMethod> getAll() {
        return (List<PaymentMethod>) repository.findAll();
    }

    public Optional<PaymentMethod> getById(Long id) {
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado con id: " + id)));
    }

    public Optional<PaymentMethod> create(PaymentMethod paymentMethod) {
        return Optional.of(repository.save(paymentMethod));
    }

    public Optional<PaymentMethod> update(Long id, PaymentMethod updatedPaymentMethod) {
        PaymentMethod existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado con id: " + id));

        existing.setName(updatedPaymentMethod.getName());
        return Optional.of(repository.save(existing));
    }

    public void delete(Long id) {
        PaymentMethod existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Método de pago no encontrado con id: " + id));
        repository.delete(existing);
    }
}
