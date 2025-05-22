package com.ufpso.Huevicola.controllers.billings;

import com.ufpso.Huevicola.models.entities.billings.DetailProducts;
import com.ufpso.Huevicola.services.billings.DetailProductsServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/detail-products")
@Tag(name = "DetailProducts", description = "Operaciones sobre los detalles de productos")
public class DetailProductsController {

    private final DetailProductsServices service;

    @Autowired
    public DetailProductsController(DetailProductsServices service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los detalles")
    @GetMapping
    public ResponseEntity<List<DetailProducts>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Obtener un detalle por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DetailProducts> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo detalle")
    @PostMapping
    public ResponseEntity<DetailProducts> create(@Valid @RequestBody DetailProducts detail) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(detail));
    }

    @Operation(summary = "Actualizar un detalle por ID")
    @PutMapping("/{id}")
    public ResponseEntity<DetailProducts> update(@PathVariable Long id, @Valid @RequestBody DetailProducts detail) {
        return service.update(id, detail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un detalle por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}