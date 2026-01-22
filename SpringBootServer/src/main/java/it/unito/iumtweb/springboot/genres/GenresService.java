package it.unito.iumtweb.springboot.genres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Genres data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class GenresService {
    private final  GenresRepository repo;

    /**
     * Constructs a new GenresService with the specified GenresRepository.
     *
     * @param repo the repository for genre entities
     */
    @Autowired
    public GenresService(GenresRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a paginated list of genres based on the movie ID.
     *
     * @param movieId the unique identifier of the movie to search for
     * @return a list of {@link Genres} objects that match the movie ID
     */
    public List<Genres> getGenresByMovieId(String movieId){
        return repo.findGenresByMovieId(movieId);
    }

    /**
     * Retrieves a list of all distinct genres in the database.
     *
     * @return a list of genre names as strings
     */
    public List<String> getAllGenres() {
        //return repo.findAll();
        return repo.findUniqueGenreNames();
    }
}
