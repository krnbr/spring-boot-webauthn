package in.neuw.passkey.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService = new InMemoryUserDetailsManager();
        String testPassword = "{noop}test";
        userDetailsService.createUser(User.withUsername("user").password(testPassword).build());
        userDetailsService.createUser(User.withUsername("admin").password(testPassword).build());
        return userDetailsService;
    }

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(ar -> {
            ar.anyRequest().authenticated();
        });
        http.formLogin(Customizer.withDefaults());
        http.logout(Customizer.withDefaults());
        http.csrf(Customizer.withDefaults());
        http.webAuthn(wc -> {
            wc.rpId("localhost").allowedOrigins("http://localhost:8080").rpName("Webauthn Demo");
        });
        return http.build();
    }

}
