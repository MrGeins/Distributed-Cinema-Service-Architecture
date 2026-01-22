package it.unito.iumtweb.springboot.genres;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Genres data.
 * It provides access to genre details filtered by movieId.
 *  All endpoints are mapped under <code>/genres</code>.
 */
@RestController
@RequestMapping("/genres")
//@CrossOrigin(origins = "*")
public class GenresController {
    private final GenresService service;

    /**
     * Constructs a new GenresController with the specified GenresService.
     *
     * @param service the service for genre entities
     */
    public GenresController(GenresService service) {
        this.service = service;
    }

    /**
     * Retrieves all genres for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Genres} entities for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity <List<Genres>> getGenres(@PathVariable String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            List<Genres> genres = service.getGenresByMovieId(movieId);
            return ResponseEntity.ok(genres);
        }

    }

    /**
     * Retrieves all genres in the database.
     *
     * @return a list of all {@link Genres} entities
     */
    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllGenres() {
        List<String> genres = service.getAllGenres();
        return ResponseEntity.ok(genres);
    }
}
