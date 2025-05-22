package com.ufpso.Huevicola.controllers.users;

import com.ufpso.Huevicola.services.users.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/github")
@CrossOrigin(origins = "http://localhost:65535")
public class GithubController {

    private final WebClient webClient = WebClient.create();
    private final String CLIENT_ID = "Ov23lis7nSID63muZdCl";
    private final String CLIENT_SECRET = "a193dec0b63814cb3cc5f2f864e80b42c0eda2b3";
    private final UserServices service;

    private String lastToken = null;

    @Autowired
    public GithubController(UserServices service) {
        this.service = service;
    }


    @PostMapping("/auth")
    public Mono<ResponseEntity<String>> authenticate(@RequestBody Map<String, String> body) {
        String code = body.get("code");

        if (code == null || code.isEmpty()) {
            throw new RuntimeException("No se pudo obtener informacion de GitHub");
        }

        Mono<Map> tokenResponse = webClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("client_id", CLIENT_ID)
                        .with("client_secret", CLIENT_SECRET)
                        .with("code", code))
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), clientResponse -> {
                    System.err.println("Error en token request: " + clientResponse.statusCode());
                    return clientResponse.bodyToMono(String.class).flatMap(bodyError -> {
                        System.err.println("Error body: " + bodyError);
                        return Mono.error(new RuntimeException("No se pudo obtener informacion de GitHub"));
                    });
                })
                .bodyToMono(Map.class);

        return tokenResponse.flatMap(tokenMap -> {
            String accessToken = (String) tokenMap.get("access_token");
            System.out.println("Access Token: " + accessToken);
            if (accessToken == null) {
                return Mono.just(ResponseEntity.ok(lastToken));
            }

            Mono<Map> userMono = webClient.get()
                    .uri("https://api.github.com/user")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(status -> status.value() == 401, clientResponse -> {
                        System.err.println("Unauthorized al obtener usuario");
                        throw new RuntimeException("No se pudo obtener informacion de GitHub");
                    })
                    .bodyToMono(Map.class);

            Mono<List<Map<String, Object>>> emailMono = webClient.get()
                    .uri("https://api.github.com/user/emails")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });

            return Mono.zip(userMono, emailMono)
                    .map(tuple -> {
                        List<Map<String, Object>> emails = tuple.getT2();

                        String primaryEmail = emails.stream()
                                .filter(e -> Boolean.TRUE.equals(e.get("primary")))
                                .map(e -> (String) e.get("email"))
                                .findFirst()
                                .orElse(null);
                        lastToken = service.loginWithEmail(primaryEmail);
                        return ResponseEntity.ok(lastToken);
                    });
        });
    }


}
