package com.ufpso.Huevicola.security;

import com.ufpso.Huevicola.services.users.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomOAuth2SuccessHandler  implements AuthenticationSuccessHandler {

    @Autowired
    private UserServices service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        try{
            String token = service.loginWithEmail(email);
            response.sendRedirect("http://localhost:65535/login-success?token=" + token);
        }catch (Exception e){
            response.sendRedirect("http://localhost:65535/");
        }

    }
}
