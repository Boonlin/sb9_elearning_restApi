package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.LoginDto;
import co.istad.elearningapi.dto.RegisterDto;
import co.istad.elearningapi.dto.VerifyDto;
import co.istad.elearningapi.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtEncoder jwtEncoder;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    Map<String, Object> register(@Valid @RequestBody RegisterDto registerDto) throws MessagingException {
        return authService.register(registerDto);
    }

    @PostMapping("/verify")
    Map<String, Object> verify(@Valid @RequestBody VerifyDto verifyDto) {
        return authService.verify(verifyDto);
    }

    @PostMapping("/login")
    Map<String, Object> login(@Valid @RequestBody LoginDto loginDto) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder().id(loginDto.email()).audience(List.of("mobile", "web")).subject("Access Token").build();
        String jwtToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        return Map.of("token", jwtToken);
    }
}
