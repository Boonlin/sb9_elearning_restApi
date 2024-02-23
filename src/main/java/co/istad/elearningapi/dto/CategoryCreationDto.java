package co.istad.elearningapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryCreationDto(
        @NotBlank
        String name,
        @NotNull
        Boolean isDeleted
) {

}
