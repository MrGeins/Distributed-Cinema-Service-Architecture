package it.unito.iumtweb.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for customizing global Spring Web MVC settings.
 * <p>
 * This class enables and configures Cross-Origin Resource Sharing (CORS)
 * for the entire application, allowing the backend to accept requests
 * from external clients such as frontend applications running on different domains
 * (e.g., React, Angular, Vue).
 * </p>
 *
 * <p>
 * By implementing {@link WebMvcConfigurer}, this configuration applies to all
 * controllers without needing to declare {@code @CrossOrigin} in each one.
 * This is particularly useful in distributed architectures where the frontend
 * and backend run on different servers or ports.
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures global CORS settings for the application.
     * <p>
     * This implementation allows:
     * <ul>
     *     <li>Requests from <code><a href="http://localhost:3000">http://localhost:3000</a></code></li>
     *     <li>HTTP methods: GET, POST, PUT, DELETE, OPTIONS</li>
     *     <li>All request headers</li>
     *     <li>Credentialed requests (cookies, authorization headers)</li>
     * </ul>
     * </p>
     *
     * @param registry the {@link CorsRegistry} used to customize CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Consenti tutte le rotte
                .allowedOrigins("http://localhost:3000") // URL del tuo client
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metodi consentiti
                .allowedHeaders("*") // Consenti tutti gli header
                .allowCredentials(true); // Consenti le credenziali
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/movies/**", "/genres/**", "/actors/**").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
