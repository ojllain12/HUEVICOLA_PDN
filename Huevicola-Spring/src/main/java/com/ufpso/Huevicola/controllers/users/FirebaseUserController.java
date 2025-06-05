package com.ufpso.Huevicola.controllers.users;

import com.ufpso.Huevicola.models.entities.products.Item;
import com.ufpso.Huevicola.models.entities.users.FirebaseUser;
import com.ufpso.Huevicola.services.users.FirebaseUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/firebase-users")
public class FirebaseUserController {


    private final FirebaseUserServices service;

    @Autowired
    public FirebaseUserController(FirebaseUserServices service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FirebaseUser>> listarUsuarios() {
        return ResponseEntity.ok().body(service.listarUsuarios());
    }
    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String uid) {
        return service.delete(uid)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
