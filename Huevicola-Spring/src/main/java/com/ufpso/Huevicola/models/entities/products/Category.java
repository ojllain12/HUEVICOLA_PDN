package com.ufpso.Huevicola.models.entities.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Schema(description = "Entidad que representa una categoría de productos")
@JsonIgnoreProperties({"category"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Por favor ingresar el nombre de la categoria de productos")
    @Schema(description = "Nombre de la categoría", example = "Alimentos")
    private String name;

    @OneToMany(mappedBy = "category")
    @Schema(description = "Lista de tipos asociados a esta categoría", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    private List<Type> listTypes;
}
