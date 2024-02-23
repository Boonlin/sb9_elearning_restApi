package co.istad.elearningapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record UserCreationDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email,
        List< @NotBlank String>roleNames
) {
}