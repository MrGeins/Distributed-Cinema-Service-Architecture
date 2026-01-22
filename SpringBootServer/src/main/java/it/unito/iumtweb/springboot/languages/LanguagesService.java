package it.unito.iumtweb.springboot.languages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Languages data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class LanguagesService {
    private final LanguagesRepository repo;

    /**
     * Constructs a new LanguagesService with the specified LanguagesRepository.
     *
     * @param repo the repository for language entities
     */
    @Autowired
    public LanguagesService(LanguagesRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a list of distinct languages for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Languages} entities for the given movie
     */
    List<Languages> findLanguagesByMovieId (String movieId) {
        return repo.findLanguagesByMovieId(movieId);
    }

    /**
     * Counts the number of distinct languages for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return the count of distinct languages for the given movie
     */
    Long countLanguagesByMovieId (String movieId) {
        return repo.countLanguagesByMovieId(movieId);
    }

    /**
     * Retrieves all languages from the repository.
     *
     * @return a list of all {@link Languages} entities
     */
    List<Languages> getAllLanguages() {
        return repo.findAll();
    }
}
