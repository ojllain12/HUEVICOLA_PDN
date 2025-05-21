package com.ufpso.Huevicola.services.users;

import com.ufpso.Huevicola.exceptions.AuthenticationFailedException;
import com.ufpso.Huevicola.models.entities.users.User;
import com.ufpso.Huevicola.models.generic.ResponseMessage;
import com.ufpso.Huevicola.repository.users.UserRepository;
import com.ufpso.Huevicola.services.JWTServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

import static com.ufpso.Huevicola.utils.Utils.createResponse;

@Service
public class UserServicesImplement implements UserServices {
    private final UserRepository repository;

    @Autowired
    public UserServicesImplement(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseMessage loginUser(User userLogin) {
        Optional<User> userOptional = repository.findByEmailAndPassword(userLogin.getEmail(), userLogin.getPassword());
        if (userOptional.isEmpty()) {
            throw new AuthenticationFailedException("Credenciales incorrectas");
        }
        return createResponse(JWTServices.getToken(new HashMap<>(), userOptional.get()), HttpStatus.OK);
    }

    @Override
    public String loginWithGoogle(String email) {
        Optional<User> userOptional = repository.findByEmail(email);
        if (userOptional.isEmpty()) {
            if(email.equals("leospinap@ufpso.edu.co")){
                User user = new User();
                user.setEmail(email);
                repository.save(user);
                return loginWithGoogle(email);
            }
            throw new AuthenticationFailedException("Credenciales incorrectas");
        }
        return JWTServices.getToken(new HashMap<>(), userOptional.get());
    }
}
