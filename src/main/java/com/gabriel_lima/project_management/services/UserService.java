package com.gabriel_lima.project_management.services;

import com.gabriel_lima.project_management.domain.entities.User;
import com.gabriel_lima.project_management.dto.UserCreateDTO;
import com.gabriel_lima.project_management.dto.UserDTO;
import com.gabriel_lima.project_management.mapper.UserMapper;
import com.gabriel_lima.project_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Cacheable(value = "users", key = "'all'")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "users", key = "#id")
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Cacheable(value = "users", key = "'email:' + #email")
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Cacheable(value = "users", key = "'project:' + #projectId")
    public List<UserDTO> getUsersByProjectId(UUID projectId) {
        return userRepository.findAllUserByProjectId(projectId).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Os métodos de criação e atualização devem invalidar o cache
    @CacheEvict(value = "users", allEntries = true)
    public UserDTO createUser(UserCreateDTO createDTO) {
        User user = userMapper.toEntity(createDTO);
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @CachePut(value = "users", key = "#id")
    @CacheEvict(value = "users", allEntries = true)
    public UserDTO updateUser(UUID id, UserCreateDTO updateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(updateDTO.getName());
        existingUser.setEmail(updateDTO.getEmail());
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }
}
