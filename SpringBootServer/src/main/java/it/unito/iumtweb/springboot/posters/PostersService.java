package it.unito.iumtweb.springboot.posters;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Posters data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class PostersService {
    private final PostersRepository repo;

    /**
     * Constructs a new PostersService with the specified PostersRepository.
     *
     * @param repo the repository for poster entities
     */
    public PostersService(PostersRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a list of distinct posters for the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Posters} entities for the given movie
     */
    public List<Posters> getPostersByMovieId(String movieId){
        return repo.findPostersByMovieId(movieId);
    }
}
