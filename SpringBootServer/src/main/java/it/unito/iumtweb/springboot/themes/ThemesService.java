package it.unito.iumtweb.springboot.themes;

import it.unito.iumtweb.springboot.studios.Studios;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Themes data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class ThemesService {
    private final ThemesRepository repo;

    /**
     * Constructs a new ThemesService with the specified ThemesRepository.
     *
     * @param repo the repository for theme entities
     */
    public ThemesService(ThemesRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a list of distinct studios for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Studios} entities for the given movie
     */
    public List<Themes> getThemesByMovieId(String movieId){
        return repo.findThemesByMovieId(movieId);
    }
}
