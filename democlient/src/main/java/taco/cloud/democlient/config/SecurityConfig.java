package taco.cloud.democlient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorizeHttpRequest ->
                authorizeHttpRequest.anyRequest().authenticated())
            .oauth2Login(oauth2Login ->
                oauth2Login.loginPage("/oauth2/authorization/taco-admin-client"))
            .oauth2Client(Customizer.withDefaults())
            .build();
    }
}
