package it.unito.iumtweb.springboot.releases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Releases data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class ReleasesService {
    private final ReleasesRepository repo;

    /**
     * Constructs a new ReleaseService with the specified ReleaseRepository.
     *
     * @param repo the repository for release entities
     */
    @Autowired
    public ReleasesService(ReleasesRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a paginated list of releases based on the movie ID.
     *
     * @param movieId the unique identifier of the movie to search for
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link Releases} objects that match the movie ID
     */
    public Page<ReleasesInfoDTO> getReleasesByMovieId(String movieId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repo.findReleasesByMovieId(movieId, pageable);
    }
}
