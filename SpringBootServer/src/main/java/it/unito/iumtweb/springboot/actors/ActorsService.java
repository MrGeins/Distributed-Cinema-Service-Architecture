package it.unito.iumtweb.springboot.actors;

import it.unito.iumtweb.springboot.movies.MoviesCardInfoDTO;
import it.unito.iumtweb.springboot.movies.MoviesRepository;
import it.unito.iumtweb.springboot.theoscarawards.TheOscarAwardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing actors-related operations.
 * Provides methods to retrieve actor names, actor details, and associated movies.
 */
@Service
public class ActorsService {
    private final ActorsRepository actorsRepo;
    private final MoviesRepository moviesRepo;
    private final TheOscarAwardsRepository theOscarAwardsRepo;

    /**
     * Constructs an ActorsService with the specified repositories.
     *
     * @param actorsRepo          the repository for managing Actors entities
     * @param moviesRepo          the repository for managing Movies entities
     * @param theOscarAwardsRepo  the repository for managing TheOscarAwards entities
     */
    @Autowired
    public ActorsService(ActorsRepository actorsRepo, MoviesRepository moviesRepo, TheOscarAwardsRepository theOscarAwardsRepo) {
        this.actorsRepo = actorsRepo;
        this.moviesRepo = moviesRepo;
        this.theOscarAwardsRepo = theOscarAwardsRepo;
    }

    /**
     * Retrieves a paginated list of actor names that match the given search value.
     *
     * @param searchValue the string to search for in actor names
     * @param page        the page number to retrieve (0-based)
     * @param size        the number of records per page
     * @return a list of actor names matching the search criteria
     */
    public List<String> getActorsNamesByString(String searchValue, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return actorsRepo.findActorNameByString(searchValue ,pageable).getContent();
    }

    /**
     * Retrieves a list of actors whose names contain the specified substring, ignoring case.
     *
     * @param name the substring to search for in actor names
     * @return a list of {@link Actors} entities whose names match the search criteria
     */
    public List<String> getActorByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // 1. Trova le entità dal DB
        return actorsRepo.findByNameContainingIgnoreCase(name, pageable)
                .stream()
                .distinct()
                .toList();
    }

    /**
     * Retrieves a paginated list of actors associated with a specific movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @param page    the page number to retrieve (0-based)
     * @param size    the number of records per page
     * @return a paginated list of {@link Actors} entities for the given movie
     */
    public Page<Actors> getActorsByMoviesId(String movieId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return actorsRepo.findActorByMovieId(movieId, pageable);
    }

    /**
     * Retrieves detailed information about an actor, including their filmography and number of Oscars won.
     * If the actor is not found, it returns null.
     *
     * @param name the name of the actor
     * @return an {@link ActorsInfoDTO} containing the actor's information, or null if the actor is not found
     */
    public ActorsInfoDTO getActorsInfo(String name) {
        List<Actors> actors = actorsRepo.findByNameIgnoreCase(name);
        if (actors.isEmpty()) {
            return null; // Actor not found
        }
        String actorName = actors.getFirst().getName();
        List<MoviesCardInfoDTO> movies = moviesRepo.findMoviesByActorOscarWinner(actorName);
        //int oscarNumber = theOscarAwardsRepo.countTheOscarAwardsByNameAndCategory(actorName, "ACTOR");
        int oscarNumber = theOscarAwardsRepo.countOscarsByActorGenericCategory(actorName);
        return new ActorsInfoDTO(actorName, movies, oscarNumber);
    }
}