package co.istad.elearningapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FileDto(
        String name,
        String extension,
        Long size,
        String uri
) {

}
