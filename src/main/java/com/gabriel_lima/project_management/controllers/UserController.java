package com.gabriel_lima.project_management.controllers;

import com.gabriel_lima.project_management.dto.*;
import com.gabriel_lima.project_management.services.JwtService;
import com.gabriel_lima.project_management.services.UserService;
import com.gabriel_lima.project_management.utils.SecurityUtils;
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
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponseDTO.success(users, "Usuários recuperados com sucesso"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponseDTO.success(user, "Usuário encontrado com sucesso"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponseDTO<AccountDTO>> getCurrentUser() {
        String email = SecurityUtils.getCurrentUserEmail();
        UserDTO user = userService.getUserByEmail(email);
        AccountDTO accountDTO = new AccountDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(ApiResponseDTO.success(accountDTO, "Dados do usuário atual recuperados com sucesso"));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponseDTO<List<UserDTO>>> getUsersByProjectId(@PathVariable UUID projectId) {
        List<UserDTO> users = userService.getUsersByProjectId(projectId);
        return ResponseEntity.ok(ApiResponseDTO.success(users, "Usuários do projeto recuperados com sucesso"));
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<UserDTO>> createUser(@RequestBody UserCreateDTO createDTO) {
        UserDTO createdUser = userService.createUser(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(createdUser, "Usuário criado com sucesso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUser(@PathVariable UUID id, @RequestBody UserCreateDTO updateDTO) {
        UserDTO updatedUser = userService.updateUser(id, updateDTO);
        return ResponseEntity.ok(ApiResponseDTO.success(updatedUser, "Usuário atualizado com sucesso"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponseDTO.success(null, "Usuário excluído com sucesso"));
    }
}
