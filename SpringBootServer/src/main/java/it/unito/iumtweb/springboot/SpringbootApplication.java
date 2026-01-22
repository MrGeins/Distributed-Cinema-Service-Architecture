package it.unito.iumtweb.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the FilmApplication Spring Boot application.
 * <p>
 * This class bootstraps the application by enabling component scanning,
 * autoconfiguration, and Spring Boot's dependency injection mechanisms.
 * It serves as the main configuration class and defines the application's
 * starting point when executed.
 * </p>
 *
 * <p>
 * Running the {@code main} method starts an embedded server (typically Tomcat)
 * and initializes all configured Spring components such as controllers,
 * services, repositories, and entities.
 * </p>
 */
@SpringBootApplication
public class SpringbootApplication {

    /**
     * Main method that launches the Spring Boot application.
     *
     * @param args command-line arguments (optional)
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
