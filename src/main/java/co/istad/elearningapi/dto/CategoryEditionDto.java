package co.istad.elearningapi.dto;

import lombok.Builder;

@Builder
public record CategoryEditionDto(
String name,
Boolean isDeleted
) {
}
