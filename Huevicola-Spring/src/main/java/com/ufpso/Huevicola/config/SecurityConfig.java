package com.ufpso.Huevicola.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufpso.Huevicola.filters.FirebaseTokenFilter;
import com.ufpso.Huevicola.filters.JWTAuthenticationFilter;
import com.ufpso.Huevicola.models.generic.ResponseMessage;
import com.ufpso.Huevicola.security.CustomOAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    //private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final FirebaseTokenFilter tokenFilter;

    @Autowired
    private CustomOAuth2SuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/ping").permitAll();
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers("api/users/**").permitAll();
                    authorize.requestMatchers("api/github/**").permitAll();
                    authorize.requestMatchers("swagger-ui/**").permitAll();
                    authorize.requestMatchers("/v3/api-docs/**").permitAll();
                    authorize.anyRequest().authenticated();
                })

                .oauth2Login(oauth -> oauth
                        .loginPage("/api/users/google")
                        .successHandler(successHandler)
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                                (request, response, authException) -> {
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.setContentType("application/json");
                                    response.setCharacterEncoding("UTF-8");
                                    response.getWriter().write(
                                            new ObjectMapper().writeValueAsString(
                                                    ResponseMessage.builder()
                                                            .message(Collections.singletonList("Se requiere una autenticacion valida para acceder a este endpoint"))
                                                            .statusCode(HttpStatus.FORBIDDEN.value())
                                                            .build()
                                            )
                                    );
                                }
                        )
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class) // ← Cambiado
                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CorsFilter(corsConfigurationSource()), ChannelProcessingFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
