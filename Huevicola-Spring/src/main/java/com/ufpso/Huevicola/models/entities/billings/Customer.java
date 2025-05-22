package com.ufpso.Huevicola.models.entities.billings;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Schema(description = "Entidad que representa a un cliente del sistema")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del cliente", example = "1")
    private Long id;

    @NotBlank(message = "Por favor ingresar el nombre del cliente")
    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    private String name;

    @NotBlank(message = "Por favor ingresar la direccion del cliente")
    @Schema(description = "Dirección del cliente", example = "Calle 123 #45-67, Barrio Centro")
    private String address;

    @NotBlank(message = "Por favor ingresar el telefono del cliente")
    @Schema(description = "Número de teléfono del cliente", example = "3001234567")
    private String phone;

    @NotBlank(message = "Por favor ingresar el nombre del cliente")
    @Schema(description = "Documento de identificación del cliente", example = "123456789")
    private String dni;

    @Schema(description = "Descuento aplicado al cliente en porcentaje", example = "10")
    private int discount;

    @OneToMany(mappedBy = "customer")
    @Schema(description = "Órdenes asociadas a este cliente", accessMode = Schema.AccessMode.READ_ONLY)
    private List<Order> orders;
}
