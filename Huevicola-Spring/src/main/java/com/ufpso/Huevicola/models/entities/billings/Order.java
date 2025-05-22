package com.ufpso.Huevicola.models.entities.billings;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UpdateTimestamp
    private LocalDateTime date;

    private float subtotal;

    private float discount;

    private float total;

    private boolean delivered;

    private LocalDateTime deliveredDate;

    private boolean paid;

    private LocalDateTime paidDate;

    @ManyToOne
    @JoinColumn(name = "paymentMethod")
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<DetailProducts> detail;
}
