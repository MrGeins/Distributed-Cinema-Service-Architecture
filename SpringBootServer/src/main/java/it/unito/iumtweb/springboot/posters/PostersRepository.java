package it.unito.iumtweb.springboot.posters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Posters} entities.
 * Provides methods for querying a list of posters by movie ID.
 */
@Repository
public interface PostersRepository extends JpaRepository<Posters, Long> {

    /**
     * Retrieves a list of distinct posters for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Posters} entities for the given movie
     */
    List<Posters> findPostersByMovieId(String movieId);
}
