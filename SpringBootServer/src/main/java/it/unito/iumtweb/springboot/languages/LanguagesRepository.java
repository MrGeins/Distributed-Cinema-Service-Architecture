package it.unito.iumtweb.springboot.languages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Languages} entities.
 * Provides methods for querying a list of languages by movie ID.
 */
@Repository
public interface LanguagesRepository extends JpaRepository<Languages, Long> {

    /**
     * Retrieves a list of distinct languages for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Languages} entities for the given movie
     */
    List<Languages> findLanguagesByMovieId(String movieId);

    /**
     * Counts the number of languages for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a number of languages of the given movie
     */
    Long countLanguagesByMovieId(String movieId);
}
