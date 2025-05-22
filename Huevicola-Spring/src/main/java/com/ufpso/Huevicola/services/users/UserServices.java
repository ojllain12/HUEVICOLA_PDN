package com.ufpso.Huevicola.services.users;

import com.ufpso.Huevicola.models.entities.users.User;
import com.ufpso.Huevicola.models.generic.ResponseMessage;

public interface UserServices {
    ResponseMessage loginUser(User user);
    String loginWithEmail(String email);
}
