package com.ufpso.Huevicola.services.users;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.ufpso.Huevicola.models.entities.users.FirebaseUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class FirebaseUserServicesImplement implements FirebaseUserServices {
    @Override
    public List<FirebaseUser> listarUsuarios() {
        List<FirebaseUser> listUser = new ArrayList<>();
        ListUsersPage page;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                FirebaseUser firebaseUser = new FirebaseUser();
                firebaseUser.setUid(user.getUid());
                firebaseUser.setEmail(user.getEmail());
                firebaseUser.setName(user.getDisplayName());
                firebaseUser.setLastAccess(Instant.ofEpochMilli(user.getUserMetadata().getLastSignInTimestamp())
                        .atZone(ZoneId.of("America/Bogota"))
                        .toLocalDateTime());
                if(firebaseUser.getEmail() != null || firebaseUser.getName() != null){
                    listUser.add(firebaseUser);
                }
            }
            page = page.getNextPage();
        }
        return listUser;
    }

    @Override
    public boolean delete(String uid) {
        try {
            FirebaseAuth.getInstance().deleteUser(uid);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }
       return true;
    }
}
