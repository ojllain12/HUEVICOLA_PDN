package com.ufpso.Huevicola.controllers.products;

import com.ufpso.Huevicola.models.entities.products.Type;
import com.ufpso.Huevicola.services.products.TypeServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/type")
@Tag(name = "Tipos de producto", description = "CRUD para gestionar tipos de producto")
public class TypeController {

    private final TypeServices service;

    @Autowired
    public TypeController(TypeServices service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los tipos de producto")
    @GetMapping
    public ResponseEntity<List<Type>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtener un tipo por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Type> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Crear un nuevo tipo de producto",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Type.class),
                            examples = @ExampleObject(
                                    name = "Tipo ejemplo",
                                    value = "{\"name\": \"Huevos\", \"category\": {\"id\": 1}}"
                            )
                    )
            )
    )
    @PostMapping
    public ResponseEntity<Type> create(@Valid @RequestBody Type type) {
        Type saved = service.save(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar un tipo de producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<Type> update(@PathVariable Long id, @Valid @RequestBody Type type) {
        return service.update(id, type)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un tipo de producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}