package com.ufpso.Huevicola.models.entities.products;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Schema(description = "Tipo de producto asociado a una categoría")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del tipo de producto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Por favor ingresar el nombre del tipo de producto")
    @Schema(description = "Nombre del tipo de producto", example = "Huevos")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category")
    @Schema(description = "Categoría a la que pertenece este tipo de producto")
    private Category category;

    @OneToMany(mappedBy = "type")
    @Schema(description = "Lista de ítems asociados a este tipo", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private List<Item> listItems;
}
