package it.unito.iumtweb.springboot.genres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Genres} entities.
 * Provides methods for querying a list of genres by movie ID.
 */
@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {

    /**
     * Retrieves a list of distinct genres for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Genres} entities for the given movie
     */
    List<Genres> findGenresByMovieId(String movieId);


    @Query("SELECT DISTINCT g.genre FROM Genres g ORDER BY g.genre ASC")
    List<String> findUniqueGenreNames();
}
