package it.unito.iumtweb.springboot.crew;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Crew data.
 * It provides access to crew details filtered by movieId.
 *  All endpoints are mapped under <code>/crew</code>.
 */
@RestController
@RequestMapping("/crew")
public class CrewController {
    private final CrewService service;

    /**
     * Constructs a new CrewController with the specified CrewService.
     *
     * @param service the service for crew entities
     */
    public CrewController(CrewService service) {
        this.service = service;
    }

    /**
     * Retrieves crew information for a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a {@link CrewInfoDTO} containing crew details for that movie
     */
    @GetMapping("/movie/{moviesId}")
    public ResponseEntity<CrewInfoDTO> getCrew(@PathVariable String movieId) {

        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        CrewInfoDTO crew = service.getCrewByMovieId(movieId);
        if (crew == null || (crew.getDirectors().isEmpty() && crew.getProducers().isEmpty() && crew.getScreenwriters().isEmpty() && crew.getOtherCredits().isEmpty())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(crew);
    }

    /**
     * Retrieves all crew members in the database.
     *
     * @return a list of all {@link Crew} entities
     */
    @GetMapping("/all")
    public ResponseEntity<List<Crew>> getAllCrew() {
        List<Crew> crew = service.getAllCrewMember();
        return ResponseEntity.ok(crew);
    }

}
