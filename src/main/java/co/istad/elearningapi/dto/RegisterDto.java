package co.istad.elearningapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;


import java.util.Set;

@Builder
public record RegisterDto(
        @NotBlank
        String username,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String confirmedPassword,
        @NotEmpty
        Set<@NotBlank String> roleNames

) {
}
