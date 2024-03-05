package co.istad.elearningapi.service;

import co.istad.elearningapi.dto.*;
import jakarta.mail.MessagingException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.Map;

public interface AuthService {
    Map<String, Object> register(RegisterDto registerDto) throws MessagingException;
    Map<String, Object> verify(VerifyDto verifyDto);

AuthDto login(LoginDto loginDto);

    AuthDto refresh(RefreshTokenDto refreshTokenDto);
}
