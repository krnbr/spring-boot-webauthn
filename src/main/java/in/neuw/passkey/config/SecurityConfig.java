package in.neuw.passkey.config;

import in.neuw.passkey.db.repositories.PasskeyCredentialRecordsJPARepository;
import in.neuw.passkey.db.repositories.UserJPARepository;
import in.neuw.passkey.db.repositories.PasskeyUserEntityJPARepository;
import in.neuw.passkey.security.CustomUserDetailsService;
import in.neuw.passkey.security.JPACustomUserCredentialRepository;
import in.neuw.passkey.security.JPAPublicKeyCredentialUserEntityRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;

@Configuration
public class SecurityConfig {

    private final String rpId;
    private final String rpName;
    final String[] allowedOrigins;

    public SecurityConfig(@Value("${webauth.rpId}") final String rpId,
                          @Value("${webauth.rpName}") final String rpName,
                          @Value("${webauth.allowedOrigins}") final String[] allowedOrigins) {
        this.rpId = rpId;
        this.rpName = rpName;
        this.allowedOrigins = allowedOrigins;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService customUserDetailsService(UserJPARepository userRepository) {
        return new CustomUserDetailsService(userRepository);
    }

    @Bean
    UserCredentialRepository userCredentialRepository(PasskeyCredentialRecordsJPARepository passkeyCredentialRecordsJPARepository) {
        return new JPACustomUserCredentialRepository(passkeyCredentialRecordsJPARepository);
    }

    @Bean
    PublicKeyCredentialUserEntityRepository publicKeyCredentialUserEntityRepository(PasskeyUserEntityJPARepository passkeyUserEntityJPARepository) {
        return new JPAPublicKeyCredentialUserEntityRepository(passkeyUserEntityJPARepository);
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(ar -> {
            ar.requestMatchers("/login/**", "/sent","/css/**", "/js/**", "/imgs/**")
                    .permitAll();
            ar.anyRequest().authenticated();
        });
        //http.formLogin(Customizer.withDefaults());
        http.formLogin(f -> f.loginPage("/login"));
        http.logout(Customizer.withDefaults());
        http.csrf(Customizer.withDefaults());
        http.webAuthn(wc -> {
            wc.rpId(rpId)
              .allowedOrigins(allowedOrigins)
              .rpName(rpName)
              .disableDefaultRegistrationPage(true);
        });
        return http.build();
    }

}
