package com.ufpso.Huevicola.services.users;

import com.ufpso.Huevicola.models.entities.users.FirebaseUser;

import java.util.List;

public interface FirebaseUserServices {
    List<FirebaseUser> listarUsuarios();
    boolean delete(String uid);
}
