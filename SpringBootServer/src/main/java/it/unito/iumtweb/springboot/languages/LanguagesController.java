package it.unito.iumtweb.springboot.languages;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Languages data.
 * It provides access to language details filtered by movieId.
 *  All endpoints are mapped under <code>/languages</code>.
 */
@RestController
@RequestMapping("/languages")
public class LanguagesController {
    private final LanguagesService service;

    /**
     * Constructs a new LanguagesController with the specified LanguagesService.
     *
     * @param service the service for language entities
     */
    public LanguagesController(LanguagesService service) {
        this.service = service;
    }

    /**
     * Retrieves the count of distinct languages for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return the count of distinct languages for that movie
     */
    @GetMapping("/count/{movieId}")
    public ResponseEntity<Long> countLanguages(@PathVariable String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().body(0L);
        }
        Long count = service.countLanguagesByMovieId(movieId);
        return ResponseEntity.ok(count);
    }

    /**
     * Retrieves all languages for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Languages} entities for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Languages>> getLanguages (@PathVariable String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Languages> languages = service.findLanguagesByMovieId(movieId);
        return ResponseEntity.ok(languages);
    }

    /**
     * Retrieves all languages in the database.
     *
     * @return a list of all {@link Languages} entities
     */
    @GetMapping("/all")
    public ResponseEntity<List<Languages>> getAllLanguages() {
        List<Languages> languages = service.getAllLanguages();
        return ResponseEntity.ok(languages);
    }
}
