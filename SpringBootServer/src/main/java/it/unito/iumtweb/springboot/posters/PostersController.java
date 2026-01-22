package it.unito.iumtweb.springboot.posters;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Posters data.
 * It provides access to poster details filtered by movieId.
 *  All endpoints are mapped under <code>/posters</code>.
 */
@RestController
@RequestMapping("/posters")
public class PostersController {
    private final PostersService service;

    /**
     * Constructs a new PostersController with the specified PostersService.
     *
     * @param service the service for poster entities
     */
    public PostersController(PostersService service) {
        this.service = service;
    }

    /**
     * Retrieves all posters for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Posters} entities for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Posters>> getMoviesPosters(@PathVariable String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            List<Posters> posters = service.getPostersByMovieId(movieId);
            return ResponseEntity.ok(posters);
        }

    }
}
