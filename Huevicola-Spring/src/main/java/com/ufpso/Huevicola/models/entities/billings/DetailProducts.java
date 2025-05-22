package com.ufpso.Huevicola.models.entities.billings;

import com.ufpso.Huevicola.models.entities.products.Item;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DetailProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cant;

    private float priceUnit;

    private float priceTotal;

    @ManyToOne
    @JoinColumn(name = "orders")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item")
    private Item item;
}
