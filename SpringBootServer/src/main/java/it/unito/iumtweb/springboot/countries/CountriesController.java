package it.unito.iumtweb.springboot.countries;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Countries data.
 * It provides access to country details filtered by movieId,
 * unique country names, and counts of movies per country.
 * All endpoints are mapped under <code>/countries</code>.
 */
@RestController
@RequestMapping("/countries")
public class CountriesController {
    private final CountriesService service;

    /**
     * Constructs a new CountriesController with the specified CountriesService.
     *
     * @param service the service for country entities
     */
    public CountriesController(CountriesService service) {
        this.service = service;
    }

    /**
     * Retrieves all countries for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Countries} entities for that movie
     */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Countries>> getCountriesByMovie(@PathVariable String movieId) {
        List<Countries> result = service.getCountriesByMovieId(movieId);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a list of unique country names from the database.
     *
     * @return a list of unique country names
     */
    @GetMapping("/names/unique")
    public List<String> getUniqueCountryNames() {
        return service.getUniqueCountryNames();
    }

    /**
     * Counts the number of movies associated with a specific country name.
     *
     * @param name the name of the country
     * @return the count of movies for the specified country
     */
    @GetMapping("/count/{name}")
    public ResponseEntity<Long> countMoviesPerCountry(@PathVariable String name) {
        Long count = service.countMoviesPerCountry(name);
        return ResponseEntity.ok(count);
    }
}
