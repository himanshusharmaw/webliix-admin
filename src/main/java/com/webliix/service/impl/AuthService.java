package com.webliix.service.impl;

import com.webliix.model.Admin;
import com.webliix.repository.AdminRepository;
import com.webliix.config.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AdminRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(AdminRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public String login(String username, String password) {
        log.debug("Login attempt for username='{}'", username);

        Admin admin = repo.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Login failed: user '{}' not found", username);
                    return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
                });

        String stored = admin.getPassword();
        if (stored == null || stored.trim().isEmpty()) {
            log.warn("Login failed: no password stored for user '{}'", username);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        String storedTrimmed = stored.trim();

        boolean matches = false;
        try {
            log.debug("Using encoder: {}", encoder.getClass().getName());
            // optional debug line, remove later
            log.debug("Raw password len={}, stored password preview={} (len={})",
                    password == null ? 0 : password.length(),
                    storedTrimmed.length() > 12 ? storedTrimmed.substring(0,6) + "..." + storedTrimmed.substring(storedTrimmed.length()-6) : storedTrimmed,
                    storedTrimmed.length()
            );

            matches = encoder.matches(password, storedTrimmed);
        } catch (Exception e) {
            log.error("Error while matching password for '{}': {}", username, e.getMessage());
        }

        if (matches) {
            log.info("Login success for '{}'", username);
            return jwtUtil.generateToken(username);
        } else {
            log.warn("Login failed for '{}': bcryptMatches={}", username, matches);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }
}
