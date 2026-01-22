package it.unito.iumtweb.springboot.crew;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Crew} entities.
 * Provides methods for querying crew members by movie ID and role.
 */
@Repository
public interface CrewRepository extends JpaRepository<Crew, Long> {
    /**
     * Retrieves a list of distinct crew members for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Crew} entities for the given movie
     */
    List<Crew> findCrewByMovieId(String movieId);

    /**
     * Retrieves the list of director names for the specified movie.
     * Only crew members whose role is exactly "director" (case-insensitive) are returned.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of director names for the given movie
     */
    @Query("SELECT DISTINCT c.name FROM Crew c WHERE LOWER(c.role) = 'director' AND c.movieId = :movieId")
    List<String> findDirectorNamesByMovieId(@Param("movieId") String movieId);

    /**
     * Retrieves the list of producer names for the specified movie.
     * Only crew members whose role is exactly "producer" (case-insensitive) are returned.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of producer names for the given movie
     */
    @Query("SELECT DISTINCT c.name FROM Crew c WHERE LOWER(c.role) = 'producer' AND c.movieId = :movieId")
    List<String> findProducerNamesByMovieId(@Param("movieId") String movieId);

    /**
     * Retrieves the list of screenwriter names for the specified movie.
     * Only crew members whose role is exactly "screenwriter" (case-insensitive) are returned.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of screenwriter names for the given movie
     */
    @Query("SELECT DISTINCT c.name FROM Crew c WHERE LOWER(c.role) = 'screenwriter' AND c.movieId = :movieId")
    List<String> findScreenwriterNamesByMovieId(@Param("movieId") String movieId);
}
