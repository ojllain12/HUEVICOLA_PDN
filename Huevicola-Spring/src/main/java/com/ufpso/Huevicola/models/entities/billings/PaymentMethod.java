package com.ufpso.Huevicola.models.entities.billings;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Por favor ingrese el nombre del metodo de pago")
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    private List<Order> order;
}
