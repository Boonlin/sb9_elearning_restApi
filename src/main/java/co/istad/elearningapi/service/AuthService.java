package co.istad.elearningapi.service;

import co.istad.elearningapi.dto.LoginDto;
import co.istad.elearningapi.dto.RegisterDto;
import co.istad.elearningapi.dto.VerifyDto;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface AuthService {

    Map<String, Object> register(RegisterDto registerDto) throws MessagingException;
    Map<String, Object> verify(VerifyDto verifyDto);

    Map<String, Object> login(LoginDto loginDto);
}
