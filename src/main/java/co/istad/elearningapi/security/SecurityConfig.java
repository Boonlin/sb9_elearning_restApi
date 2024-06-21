package co.istad.elearningapi.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService);
        dao.setPasswordEncoder(passwordEncoder);
        return dao;
    }

    @Bean
    @Primary
    public KeyPair keyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Primary
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
    }

    @Bean
    @Primary
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) {
                return jwkSelector.select(jwkSet);
            }
        };
    }

    @Bean
    @Primary
    JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean
    @Primary
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }
@Bean
    JwtAuthenticationConverter jwtAuthenticationConverter (){
      return new JwtAuthenticationConverter();
}
@Bean
JwtAuthenticationProvider jwtAuthenticationProvider (@Qualifier("jwtRefreshTokenJwtDecoder") JwtDecoder jwtRefreshTokenJwtDecoder){
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshTokenJwtDecoder);
                provider.setJwtAuthenticationConverter(jwtAuthenticationConverter());
     return provider;
}
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity ,JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        // Your security logic
        httpSecurity.authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/api/v1/files/**").permitAll()
                                .requestMatchers("/api/v1/roles/**").permitAll()
                                .requestMatchers("/api/v1/users/**").permitAll()
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll() // Adjust this path if necessary
                                .anyRequest().authenticated()
                )
                // security mechanism (username & password)
                //.httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer( oauth2-> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .csrf(token -> token.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }

    @Bean(name = "jwtRefreshTokenKeyPair")
    public KeyPair jwtRefreshTokenKeyPair() {
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name ="jwtRefreshTokenRsaKey")
    public RSAKey jwtRefreshTokenRsaKey(@Qualifier("jwtRefreshTokenKeyPair") KeyPair keyPair) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey(keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build();
    }

    @Bean(name ="jwtRefreshTokenJwkSource")
    public JWKSource<SecurityContext> jwtRefreshTokenJwkSource(@Qualifier("jwtRefreshTokenRsaKey") RSAKey rsaKey) {
        var jwkSet = new JWKSet(rsaKey);
        return new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) {
                return jwkSelector.select(jwkSet);
            }
        };
    }

    @Bean(name ="jwtRefreshTokenJwtDecoder")
    JwtDecoder jwtRefreshTokenJwtDecoder(@Qualifier("jwtRefreshTokenRsaKey") RSAKey rsaKey) throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    @Bean(name ="jwtRefreshTokenJwtEncoder")
    JwtEncoder jwtRefreshTokenJwtEncoder(@Qualifier("jwtRefreshTokenJwkSource") JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

}
