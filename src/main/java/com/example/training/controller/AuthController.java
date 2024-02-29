package com.example.training.controller;

import com.example.training.model.api.LoginRequest;
import com.example.training.model.api.LoginResponse;
import com.example.training.service.auth.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/token/refresh")
    public LoginResponse refreshToken(@RequestHeader("Authorization") String authorization) {
        String headerAuth = authorization.substring(7);
        return authService.refresh(headerAuth);
    }
}
