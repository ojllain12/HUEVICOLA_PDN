package com.ufpso.Huevicola.models.entities.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ufpso.Huevicola.models.entities.billings.DetailProducts;
import com.ufpso.Huevicola.models.entities.users.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Schema(description = "Entidad que representa un producto en el sistema")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del producto", example = "1")
    private Long id;

    @NotBlank(message = "Por favor ingresar el nombre del producto")
    @Schema(description = "Nombre del producto", example = "Pan Integral")
    private String name;

    @UpdateTimestamp
    @Schema(description = "Fecha de la última actualización del producto", example = "2024-05-08T13:45:00")
    private LocalDateTime date;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Schema(description = "Cantidad disponible en inventario", example = "100")
    private Long stock;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio del producto", example = "5000")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "type")
    @Schema(description = "Tipo de producto al que pertenece")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "users")
    @Schema(description = "Usuario que registró el producto")
    private User user;

    @OneToMany(mappedBy = "item")
    @Schema(description = "Lista de detalles asociados al producto", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private List<DetailProducts> detail;
}
