package com.ufpso.Huevicola.controllers.products;

import com.ufpso.Huevicola.models.entities.products.Item;
import com.ufpso.Huevicola.services.products.ItemServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/item")
@Tag(name = "Item", description = "Operaciones relacionadas con productos")
public class ItemController {

    private final ItemServices service;

    @Autowired
    public ItemController(ItemServices service) {
        this.service = service;
    }

    @Operation(summary = "Obtener todos los productos")
    @GetMapping
    public ResponseEntity<List<Item>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Operation(summary = "Buscar producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponse(responseCode = "201", description = "Producto creado")
    @PostMapping
    public ResponseEntity<Item> create(@Valid @RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(item));
    }

    @Operation(summary = "Actualizar un producto existente")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @Valid @RequestBody Item item) {
        return service.update(id, item)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("Pego!");
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}