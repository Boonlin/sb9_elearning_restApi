package co.istad.elearningapi.dto;

import lombok.Builder;

@Builder
public record AuthDto(
        String tokenType,
        String accessToken,
        String refeshToken
) {
}
