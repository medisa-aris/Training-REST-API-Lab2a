package com.training.lab2a.controller;

import com.training.lab2a.dto.LoginRequest;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtEncoder encoder;

    public AuthController(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        // Demo mapping only. In production, validate password hash before issuing a token.
        var roles = switch (req.username()) {
            case "admin" -> List.of("ADMIN", "MANAGER", "AGENT");
            case "agent" -> List.of("AGENT");
            default -> List.of("CUSTOMER");
        };

        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("acme-lab")
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .subject(req.username())
                .audience(List.of("acme-api"))
                .claim("roles", roles)
                .build();

        var token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return Map.of("access_token", token);
    }
}
