package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.AccountDTO;
import com.gabriel_lima.project_management.dto.UserCreateDTO;
import com.gabriel_lima.project_management.dto.UserDTO;
import com.gabriel_lima.project_management.services.JwtService;
import com.gabriel_lima.project_management.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/me")
    public ResponseEntity<AccountDTO> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.getSubject(token);
        UserDTO user = userService.getUserByEmail(email);
        AccountDTO accountDTO = new AccountDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(accountDTO);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<UserDTO>> getUsersByProjectId(@PathVariable UUID projectId) {
        List<UserDTO> users = userService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO createDTO) {
        UserDTO createdUser = userService.createUser(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserCreateDTO updateDTO) {
        UserDTO updatedUser = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
