package com.webliix.controller;

import com.webliix.service.impl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req) {
        String token = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    public static class LoginRequest {
        private String username;
        private String password;
        public LoginRequest() {}
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class TokenResponse {
        private String token;
        public TokenResponse() {}
        public TokenResponse(String token) { this.token = token; }
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }
}
