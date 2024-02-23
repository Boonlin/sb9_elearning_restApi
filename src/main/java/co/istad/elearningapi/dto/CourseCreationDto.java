package co.istad.elearningapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record CourseCreationDto(@NotBlank String title,
                                @NotBlank String thumbnail,
                                @NotBlank String description,
                                @NonNull @Positive Long categoryId,
                                @NonNull @Positive Long instructorId,
                                 @NotNull
                                Boolean isFree




) {
}
