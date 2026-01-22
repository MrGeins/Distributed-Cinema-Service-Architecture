package it.unito.iumtweb.springboot.studios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Studios} entities.
 * Provides methods for querying a list of studios by movie ID.
 */
@Repository
public interface StudiosRepository extends JpaRepository<Studios, Long> {
    /**
     * Retrieves a list of distinct studios for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Studios} entities for the given movie
     */
    List<Studios> findStudiosByMovieId(String movieId);
}
