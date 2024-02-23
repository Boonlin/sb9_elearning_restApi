package co.istad.elearningapi.dto;

import co.istad.elearningapi.model.Authority;

import java.util.List;

public record RoleDto(
        String name,
        List<String> authorities
) {
}
