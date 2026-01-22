package it.unito.iumtweb.springboot.movies;

import it.unito.iumtweb.springboot.themes.Themes;
import it.unito.iumtweb.springboot.themes.ThemesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *  Service class that provides high-level operations for retrieving and analyzing
 * Movies data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class MoviesService {
    private final MoviesRepository moviesRepo;
    private final ThemesRepository themesRepo;

    /**
     * Constructs a new MoviesService with the specified MoviesRepository and ThemesRepository.
     *
     * @param moviesRepo the repository for movie entities
     * @param themesRepo the repository for theme entities
     */
    public MoviesService(MoviesRepository moviesRepo, ThemesRepository themesRepo) {
        this.moviesRepo = moviesRepo;
        this.themesRepo = themesRepo;
    }

    /**
     * Retrieves a paginated list of movies featuring the specified actor, ignoring case.
     * The results are paginated and sorted by rating in descending order.
     *
     * @param name the name of the actor (case-insensitive)
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies featuring the actor
     */
    public Page<MoviesCardInfoDTO> getMovieByActor(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"rating"));
        return moviesRepo.findMoviesByActor(name, pageable);
    }

    /**
     * Retrieves a paginated list of movies whose titles contain the specified string, ignoring case.
     * The results are paginated and sorted by rating in descending order.
     *
     * @param title the title or partial title to search for (case-insensitive)
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects matching the search criteria
     */
    public Page<MoviesCardInfoDTO> getMovieByTitle(String title,int page, int size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"rating"));
        return moviesRepo.findMoviesByTitle(title, pageable);
    }

    /**
     * Retrieves a paginated list of movies filtered by the specified genre.
     * The results are paginated and sorted by rating in descending order.
     *
     * @param genre the genre to filter movies
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies filtered by genre
     */
    Page<MoviesCardInfoDTO> getMoviesByGenre(String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"rating"));
        return moviesRepo.findMoviesByGenre(genre, pageable);
    }

    /**
     * Retrieves a paginated list of movies released in the specified year.
     * The results are paginated and sorted by movie name in descending order.
     *
     * @param year the year of release to filter movies
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies released in the specified year
     */
    public Page<MoviesCardInfoDTO> getMoviesByYear(String year, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").descending());
        return moviesRepo.findMoviesByDate(year, pageable);
    }

    /**
     * Retrieves a paginated list of movies with the specified rating.
     * The results are paginated and sorted by rating in descending order.
     *
     * @param rating the rating to filter movies
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies with the specified rating
     */
    public Page<MoviesCardInfoDTO> getMoviesByRating(Double rating, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return moviesRepo.findMoviesByRating(rating, pageable);
    }

    /**
     * Retrieves a paginated list of movies filtered and sorted according to the provided options.
     * Allows sorting by rating, year of release, or movie title, and filtering by genres.
     *
     * @param sort the sorting criteria ("newest", "movie", or default "rating")
     * @param genres a list of genres to filter by; if null or empty, no genre filtering is applied
     * @param order the sort order ("ASC" for ascending, otherwise descending)
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects matching the criteria
     */
    public Page<MoviesCardInfoDTO> getMoviesByOptions(String sort, String genres, String year, String order, Integer page, Integer size) {


        String sorting = switch (sort) {
            case "year", "newest" -> "date";
            case "movie", "title" -> "name";
            default -> "rating";
        };

        Sort.Direction direction = (order != null && order.equalsIgnoreCase("ASC")) ? Sort.Direction.ASC : Sort.Direction.DESC;

        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sorting).and(Sort.by(Sort.Direction.ASC, "movie_id")));


        String genreParam = (genres == null || genres.isEmpty() ||  genres.equals("all"))
                ? null : genres;


        String yearParam = (year == null || year.trim().isEmpty() || year.equalsIgnoreCase("all"))
                ? null : year;


        return moviesRepo.findMoviesWithSmartFilters(genreParam, yearParam, pageable);
    }

    List<MoviesCardInfoDTO> getMoviesByActorOscarWinner(String name) {
        return moviesRepo.findMoviesByActorOscarWinner(name);
    }

    /**
     * Finds movies similar to the specified movie based on shared themes.
     * Retrieves a paginated list of movies that share at least 5 themes with the given movie,
     * excluding the movie itself.
     *
     * @param movieId the unique identifier of the reference movie
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects representing related movies
     */
    public List<MoviesCardInfoDTO> getRelatedMoviesByThemes(String movieId, Integer page, Integer size) {
        List<Themes> themes = themesRepo.findThemesByMovieId(movieId);
        List<String> themesNames = themes.stream().map(Themes::getTheme).toList();

        Pageable pageable = PageRequest.of(page, size);
        Page<MoviesCardInfoDTO> pageOfMovies = moviesRepo.findMoviesWithMinimumThemes(themesNames, movieId, 5, pageable);

        System.out.println("Related movies: " + pageOfMovies.getContent());
        return pageOfMovies.getContent();
    }


    /**
     * shows movies details on click
     * @param movieId
     * @return
     */
    public Page<MoviesCardInfoDTO> getMoviesDetail(String movieId) {
        // Usa moviesRepo (istanza)
        Pageable pageable = PageRequest.of(0, 1);
        Page<MoviesCardInfoDTO> result = moviesRepo.findMovieDetailById(movieId,pageable);
        return result;
    }

    /**
     * Adds a new movie to the database.
     *
     * @param movie the {@link Movies} entity to be saved
     */
    public void addMovie(Movies movie) {
        moviesRepo.save(movie);
    }

    /**
     * Retrieves all movies from the database.
     *
     * @return a list of all {@link Movies} entities
     */
    public List<Movies> getAllMovies() {
        return moviesRepo.findAll();
    }



    public List<String> getAllYears() {
        return moviesRepo.findAllDistinctYears();
    }


}
