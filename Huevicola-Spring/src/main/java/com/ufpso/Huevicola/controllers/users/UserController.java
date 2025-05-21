package com.ufpso.Huevicola.controllers.users;

import com.ufpso.Huevicola.models.entities.users.User;
import com.ufpso.Huevicola.models.generic.ResponseMessage;
import com.ufpso.Huevicola.services.users.UserServices;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserServices service;
    @Autowired
    public UserController(UserServices service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseMessage loginUser(@RequestBody User user) {
        return service.loginUser(user);
    }

    @GetMapping("/google")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }
}
