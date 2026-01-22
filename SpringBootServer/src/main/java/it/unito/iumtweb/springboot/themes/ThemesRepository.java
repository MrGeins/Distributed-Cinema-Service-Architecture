package it.unito.iumtweb.springboot.themes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Themes} entities.
 * Provides methods for querying a list of themes by movie ID.
 */
@Repository
public interface ThemesRepository extends JpaRepository<Themes, Long> {

    /**
     * Retrieves a list of distinct releases for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Themes} entities for the given movie
     */
    List<Themes> findThemesByMovieId(String movieId);
}
