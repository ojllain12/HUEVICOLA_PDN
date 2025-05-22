package com.ufpso.Huevicola.controllers.billings;

import com.ufpso.Huevicola.models.entities.billings.PaymentMethod;
import com.ufpso.Huevicola.services.billings.PaymentMethodServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment-method")
@Tag(name = "Payment Method", description = "Operaciones relacionadas con los métodos de pago")
public class PaymentMethodController {

    private final PaymentMethodServices service;

    @Autowired
    public PaymentMethodController(PaymentMethodServices service) {
        this.service = service;
    }


    @Operation(summary = "Listar todos los métodos de pago")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentMethod.class)))
    @GetMapping
    public ResponseEntity<List<PaymentMethod>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Obtener un método de pago por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentMethod.class))),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id).get());
    }

    @Operation(summary = "Crear un nuevo método de pago")
    @ApiResponse(responseCode = "201", description = "Método de pago creado exitosamente",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentMethod.class)))
    @PostMapping
    public ResponseEntity<PaymentMethod> create(
            @RequestBody(description = "Datos del método de pago a crear", required = true)
            @org.springframework.web.bind.annotation.RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.status(201).body(service.create(paymentMethod).get());
    }

    @Operation(summary = "Actualizar un método de pago existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Método de pago actualizado"),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethod> update(@PathVariable Long id,
                                                @RequestBody(description = "Datos actualizados del método de pago", required = true)
                                                @org.springframework.web.bind.annotation.RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.ok(service.update(id, paymentMethod).get());
    }

    @Operation(summary = "Eliminar un método de pago por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Método de pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Método de pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}