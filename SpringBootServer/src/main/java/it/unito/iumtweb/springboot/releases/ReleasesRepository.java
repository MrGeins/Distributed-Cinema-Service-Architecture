package it.unito.iumtweb.springboot.releases;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Releases} entities.
 * Provides methods for querying releases by movie ID with pagination.
 * Extends {@link JpaRepository} to provide standard CRUD operations.
 */
@Repository
public interface ReleasesRepository extends JpaRepository<Releases, Long> {

    /**
     * Retrieves a paginated list of distinct releases for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @param pageable the pagination information
     * @return a {@link Page} of {@link ReleasesInfoDTO} object for the given movie
     */
    Page<ReleasesInfoDTO> findReleasesByMovieId(String movieId, Pageable pageable);

}
