package it.unito.iumtweb.springboot.actors;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes endpoints for managing and retrieving actor-related information.
 * <p>
 * Handles API requests including listing actors based on search criteria, fetching actors by movie ID,
 * and retrieving detailed information about a specific actor.
 * </p>
 * <p>
 * All endpoints in this controller are mapped under <code>/actors</code>.
 * </p>
 */
@RestController
@RequestMapping("/actors")
public class ActorsController {
    private final ActorsService service;

    /**
     * Constructs an {@link ActorsController} with the provided service dependency.
     *
     * @param service Service for handling actor-related operations
     */
    public ActorsController(ActorsService service) {
        this.service = service;
    }

    /**
     * Retrieves a list of actors whose names match the given search value.
     *
     * @param searchValue the string to search for in actor names
     * @param page        the page number to retrieve (default is 0)
     * @param size        the number of records per page (default is 12)
     * @return a ResponseEntity containing a list of Actors matching the search criteria
     */
    @GetMapping("/list")
    public ResponseEntity<List<String>> getActors(@RequestParam String searchValue,
                                                  @RequestParam (defaultValue = "0") int page,
                                                  @RequestParam (defaultValue = "12") int size) {
        if (searchValue == null || searchValue.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<String> actors = service.getActorByName(searchValue, page, size);
        return ResponseEntity.ok(actors);
    }

    /**
     * Retrieves a paginated list of actors associated with a specific movie ID.
     *
     * @param movieId the ID of the movie
     * @param page    the page number to retrieve (default is 0)
     * @param size    the number of records per page (default is 12)
     * @return a ResponseEntity containing a paginated list of Actors for the specified movie ID
     */
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Page<Actors>> getActorsByMovieId(@PathVariable String movieId,
                                                           @RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "12") Integer size) {
        if (movieId == null || movieId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Page<Actors> actors = service.getActorsByMoviesId(movieId, page, size);
        return ResponseEntity.ok(actors);
    }

    /**
     * Retrieves detailed information about an actor by their name.
     *
     * @param name the name of the actor
     * @return a ResponseEntity containing an ActorsInfoDTO with detailed actor information
     */
    @GetMapping("/info/{name}")
    public ResponseEntity<ActorsInfoDTO> getActorsInfo(@PathVariable String name) {
        if (name == null || name.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        ActorsInfoDTO dto = service.getActorsInfo(name);
//        if (dto == null) {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(dto);
    }
}
