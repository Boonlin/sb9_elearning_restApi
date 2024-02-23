package co.istad.elearningapi.dto;

import jakarta.validation.constraints.NotBlank;
public record VerifyDto(
        @NotBlank
        String email,
        @NotBlank
        String verifiedCode
) {
}
