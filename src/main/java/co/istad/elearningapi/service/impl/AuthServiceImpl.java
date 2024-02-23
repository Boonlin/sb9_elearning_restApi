package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.RegisterDto;
import co.istad.elearningapi.dto.UserCreationDto;
import co.istad.elearningapi.dto.VerifyDto;
import co.istad.elearningapi.mapper.UserMapper;
import co.istad.elearningapi.model.User;
import co.istad.elearningapi.repository.AuthRepository;
import co.istad.elearningapi.service.AuthService;
import co.istad.elearningapi.service.UserService;
import co.istad.elearningapi.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthRepository authRepository;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String adminMail;

    @Transactional
    @Override
    public Map<String, Object> register(RegisterDto registerDto) throws MessagingException {
        if (!registerDto.password().equals(registerDto.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password doesn't math!");
        }
        UserCreationDto userCreationDto = userMapper.mapRegisterDtoToUserCreationDto(registerDto);
        userService.createNew(userCreationDto);
        // Update verified code into db
        String sixDigits = RandomUtil.random6Digits();
        authRepository.updateVerifiedCode(registerDto.email(), sixDigits);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject(" Account Verification");
        mimeMessageHelper.setText(sixDigits);
        mimeMessageHelper.setTo(registerDto.email());
        mimeMessageHelper.setFrom(adminMail);
        javaMailSender.send(mimeMessage);

        return Map.of("message", "Please check email and verify", "email", registerDto.email(), "username", registerDto.username(), "roleUser", userCreationDto.roleNames());
    }
    @Override
    public Map<String, Object> verify(VerifyDto verifyDto) {
        User user = authRepository.findByEmailAndVerifiedCode(verifyDto.email(), verifyDto.verifiedCode()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User has not been found"));
        user.setIsVerified(true);
        user.setVerifiedCode(null);
        authRepository.save(user);
        return Map.of("message", "Your account has been verified", "email", verifyDto.email());
    }
}
