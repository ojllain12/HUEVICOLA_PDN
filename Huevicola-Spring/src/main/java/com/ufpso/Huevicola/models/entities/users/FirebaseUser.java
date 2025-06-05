package com.ufpso.Huevicola.models.entities.users;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class FirebaseUser {
    private String uid;
    private String name;
    private String email;
    private LocalDateTime lastAccess;
}
