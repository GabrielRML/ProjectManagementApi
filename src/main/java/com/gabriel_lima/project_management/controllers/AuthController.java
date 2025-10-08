package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.*;
import com.gabriel_lima.project_management.services.JwtService;
import com.gabriel_lima.project_management.repositories.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@SecurityRequirements({})
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<AuthenticationResponseDTO>> login(@RequestBody AuthenticationDTO body) {
        Optional<User> userOpt = this.userRepository.findByEmail(body.email());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error("Credenciais inválidas", "Usuário não encontrado"));
        }

        User user = userOpt.get();
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            var tokenResponse = jwtService.generateTokenResponse(user);
            return ResponseEntity.ok(ApiResponseDTO.success(tokenResponse, "Login realizado com sucesso"));
        }

        return ResponseEntity.badRequest()
                .body(ApiResponseDTO.error("Credenciais inválidas", "Senha incorreta"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<AuthenticationResponseDTO>> register(@RequestBody CreateUserDTO body) {
        Optional<User> existingUser = this.userRepository.findByEmail(body.email());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponseDTO.error("Email já está em uso", "Usuário já existe com este email"));
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setRole(body.role());
        this.userRepository.save(newUser);

        var tokenResponse = jwtService.generateTokenResponse(newUser);
        return ResponseEntity.ok(ApiResponseDTO.success(tokenResponse, "Usuário registrado com sucesso"));
    }
}
