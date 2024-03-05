package co.istad.elearningapi.service.impl;

import co.istad.elearningapi.dto.*;
import co.istad.elearningapi.mapper.UserMapper;
import co.istad.elearningapi.model.User;
import co.istad.elearningapi.repository.AuthRepository;
import co.istad.elearningapi.service.AuthService;
import co.istad.elearningapi.service.UserService;
import co.istad.elearningapi.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthRepository authRepository;
    private final JavaMailSender javaMailSender;
    private  JwtEncoder jwtRefreshTokenJwtEncoder;
    private final JwtEncoder jwtEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
@Autowired
@Qualifier("jwtRefreshTokenJwtEncoder")
    public void setJwtRefreshTokenJwtEncoder(JwtEncoder jwtRefreshTokenJwtEncoder) {
        this.jwtRefreshTokenJwtEncoder = jwtRefreshTokenJwtEncoder;
    }
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

    @Override
    public AuthDto login(LoginDto loginDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDto.email(),
                loginDto.password()
        );
        auth = daoAuthenticationProvider.authenticate(auth);

        log.info("Auth: {}", auth);
        log.info("Auth: {}", auth.getName());
        log.info("Auth: {}", auth.getAuthorities());

        return AuthDto.builder()
                .tokenType("Bearer")
                .accessToken(this.createAccessToken(auth))
                .refeshToken(this.createRefreshToken(auth))
                .build();
    }

    private String createAccessToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(authentication.getName())
                .audience(List.of("Mobile, ", "Web"))
                .issuedAt(now)
                .claim( "scope", scope)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .issuer(authentication.getName())
                .subject("Access Token")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters
                .from(jwtClaimsSet)).getTokenValue();

    }

    @Override
    public AuthDto refresh(RefreshTokenDto refreshTokenDto) {
           Authentication auth = new BearerTokenAuthenticationToken(refreshTokenDto.refreshToken());
           auth = jwtAuthenticationProvider.authenticate(auth);
           return AuthDto.builder()
                   .tokenType("Bearer")
                   .accessToken(this.createAccessToken(auth))
                   .refeshToken(this.createRefreshToken(auth))
                   .build();
    }

    private String createRefreshToken( Authentication authentication){
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(authentication.getName())
                .audience(List.of("Mobile, ", "Web"))
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .issuer(authentication.getName())
                .subject("Refresh Token")
                .build();
        return jwtRefreshTokenJwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

    }
}
