package it.unito.iumtweb.springboot.studios;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Studios data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class StudiosService {
    private final StudiosRepository repo;

    /**
     * Constructs a new StudiosService with the specified StudiosRepository.
     *
     * @param repo the repository for studio entities
     */
    public StudiosService(StudiosRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a list of distinct studios for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Studios} entities for the given movie
     */
    public List<Studios> getStudiosByMovieId(String movieId){
        return repo.findStudiosByMovieId(movieId);
    }
}
