package it.unito.iumtweb.springboot.studios;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller that exposes endpoints for querying Studios data.
 * It provides access to studio details filtered by movieId.
 *  All endpoints are mapped under <code>/studios</code>.
 */
@RestController
@RequestMapping("/studios")
public class StudiosController {
    private final StudiosService service;

    /**
     * Constructs a new StudiosController with the specified StudiosService.
     *
     * @param service the service for studio entities
     */
    public StudiosController(StudiosService service) {
        this.service = service;
    }

    /**
     * Retrieves all studios for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Studios} entities for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Studios>> getMoviesStudios(@PathVariable String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        else {
            List<Studios> releases = service.getStudiosByMovieId(movieId);
            return ResponseEntity.ok(releases);
        }

    }
}
