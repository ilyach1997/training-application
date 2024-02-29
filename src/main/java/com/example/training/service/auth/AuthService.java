package com.example.training.service.auth;

import com.example.training.model.api.LoginRequest;
import com.example.training.model.api.LoginResponse;
import com.example.training.model.domain.AccountDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public static final int AMOUNT_TO_ADD_ACCESS_TOKEN = 2;
    public static final int AMOUNT_TO_ADD_REFRESH_TOKEN = 10;

    private final TokenService tokenService;
    private final AuthenticationManager authManager;
    private final AccountDetailsService accountDetailsService;

    public AuthService(TokenService tokenService, AuthenticationManager authManager, AccountDetailsService accountDetailsService) {
        this.tokenService = tokenService;
        this.authManager = authManager;
        this.accountDetailsService = accountDetailsService;
    }

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.username(), request.password());
        authManager.authenticate(authenticationToken);
        return getLoginResponse(request.username());
    }

    public LoginResponse refresh(String headerAuth) {
        String username = tokenService.parseToken(headerAuth);
        return getLoginResponse(username);
    }

    private LoginResponse getLoginResponse(String username) {
        AccountDetails user = (AccountDetails) accountDetailsService.loadUserByUsername(username);
        String accessToken = tokenService.getTokenValue(user, AMOUNT_TO_ADD_ACCESS_TOKEN);
        String refreshToken = tokenService.getTokenValue(user, AMOUNT_TO_ADD_REFRESH_TOKEN);
        return new LoginResponse(accessToken, refreshToken);
    }
}
