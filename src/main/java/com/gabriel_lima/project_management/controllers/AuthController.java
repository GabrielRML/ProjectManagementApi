package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.AuthenticationDTO;
import com.gabriel_lima.project_management.dto.AuthenticationResponseDTO;
import com.gabriel_lima.project_management.dto.CreateUserDTO;
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
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody AuthenticationDTO body) {
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User Not Found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            var tokenResponse = jwtService.generateTokenResponse(user);
            return ResponseEntity.ok(tokenResponse);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody CreateUserDTO body) {
        Optional<User> user = this.userRepository.findByEmail(body.email());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setRole(body.role());
        this.userRepository.save(newUser);

        var tokenResponse = jwtService.generateTokenResponse(newUser);
        return ResponseEntity.ok(tokenResponse);
    }
}
