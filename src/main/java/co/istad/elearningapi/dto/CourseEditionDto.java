package co.istad.elearningapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public record CourseEditionDto(
        @NotBlank String title,
        @NotBlank String thumbnail,
        @NotBlank String description,
        @NotNull
        Boolean isFree
) {
}
//title, thumbnail, description, isFree, isDeleted)
