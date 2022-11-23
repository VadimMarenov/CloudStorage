package ru.maren.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.maren.cloudstorage.entity.AuthTokenEntity;
import ru.maren.cloudstorage.model.request.LoginRequest;
import ru.maren.cloudstorage.model.responce.LoginResponse;
import ru.maren.cloudstorage.service.AuthService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public LoginResponse login(@Valid @RequestBody LoginRequest loginInRequest) {
        return authService.login(loginInRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") AuthTokenEntity authToken) {
        authService.logout(authToken);
    }
}
