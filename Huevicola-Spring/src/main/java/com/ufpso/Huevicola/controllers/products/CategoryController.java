package com.ufpso.Huevicola.controllers.products;

import com.ufpso.Huevicola.models.entities.products.Category;
import com.ufpso.Huevicola.services.products.CategoryServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@Tag(name = "Categorías", description = "CRUD de categorías de productos")
public class CategoryController {

    private final CategoryServices service;

    @Autowired
    public CategoryController(CategoryServices service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas las categorías", responses = {
            @ApiResponse(responseCode = "200", description = "Listado exitoso")
    })
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener una categoría por su ID", responses = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear una nueva categoría",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto Category a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Category.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de categoría",
                                    value = "{\"name\": \"Alimentos\"}"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Error de validación")
            }
    )
    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        Category saved = service.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar una categoría existente", responses = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody Category category) {
        return service.update(id, category)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una categoría", responses = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}