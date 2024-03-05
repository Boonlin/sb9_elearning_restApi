package co.istad.elearningapi.controller;

import co.istad.elearningapi.dto.*;
import co.istad.elearningapi.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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
    AuthDto login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }
    @PostMapping("/refresh")
    AuthDto refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto){
        return authService.refresh(refreshTokenDto);
    }

}
