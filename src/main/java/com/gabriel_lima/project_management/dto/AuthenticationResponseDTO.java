package com.gabriel_lima.project_management.dto;

import java.time.Instant;

public record AuthenticationResponseDTO(String token, Instant expiration) {
}
