package it.unito.iumtweb.springboot.themes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Themes data.
 * It provides access to theme details filtered by movieId.
 * All endpoints are mapped under <code>/themes</code>.
 */
@RestController
@RequestMapping("/themes")
public class ThemesController {
    private final ThemesService repo;

    /**
     * Constructs a new ThemesController with the specified ThemesService.
     *
     * @param repo the service for theme entities
     */
    public ThemesController(ThemesService repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all themes for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Themes} entities for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Themes>> getMoviesThemes(@PathVariable String movieId) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            List<Themes> themes = repo.getThemesByMovieId(movieId);
            return ResponseEntity.ok(themes);
        }

    }
}
