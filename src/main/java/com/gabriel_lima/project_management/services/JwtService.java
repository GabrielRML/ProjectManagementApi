package com.gabriel_lima.project_management.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.AuthenticationResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    @Value("${api.security.token.secret}")
    private String secret;

    public AuthenticationResponseDTO generateTokenResponse(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expiration = this.generateExpirationDate();

            String token = JWT.create()
                    .withIssuer("ProjectManagementAPI")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiration)
                    .sign(algorithm);

            return new AuthenticationResponseDTO(token, expiration);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    private Instant generateExpirationDate() {
        return Instant.now().plusSeconds(3600);
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ProjectManagementAPI")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("ProjectManagementAPI")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }
}
