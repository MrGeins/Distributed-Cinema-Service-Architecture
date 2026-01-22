package it.unito.iumtweb.springboot.movies;

import it.unito.iumtweb.springboot.actors.ActorsService;
import it.unito.iumtweb.springboot.crew.CrewService;
import it.unito.iumtweb.springboot.genres.GenresService;
import it.unito.iumtweb.springboot.posters.PostersService;
import it.unito.iumtweb.springboot.releases.ReleasesService;
import it.unito.iumtweb.springboot.studios.StudiosService;
import it.unito.iumtweb.springboot.themes.ThemesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes endpoints for managing and retrieving movie-related information.
 * <p>
 * Handles API requests including listing movies with filter and sort options, fetching details for a specific movie,
 * adding a new movie, and generating movie suggestions based on themes.
 * </p>
 * <p>
 * All endpoints in this controller are mapped under <code>/movies</code>.
 * </p>
 */
@RestController
@RequestMapping("/movies")
//@CrossOrigin(originPatterns = "http://localhost:[*]", allowCredentials = "true")
public class MoviesController {
    private final MoviesService moviesService;
    private final ActorsService actorsService;
    private final CrewService crewService;
    private final GenresService genresService;
    private final PostersService postersService;
    private final ReleasesService releasesService;
    private final StudiosService studiosService;
    private final ThemesService themesService;

    /**
    * Constructs a {@link MoviesController} with the provided service dependencies.
    *
    * @param moviesService    Service for handling basic movie data and operations
    * @param actorsService   Service for actor-related operations
    * @param crewService     Service for crew information (director, writers, etc.)
    * @param genresService    Service for managing movie genres
    * @param postersService  Service for handling poster image retrieval
    * @param releasesService  Service for release date/classification data
    * @param studiosService   Service for accessing movie studio info
    * @param themesService    Service for movie themes (categories, keywords, etc.)
    */
    @Autowired
    public MoviesController(MoviesService moviesService, ActorsService actorsService, CrewService crewService, GenresService genresService, PostersService postersService, ReleasesService releasesService, StudiosService studiosService, ThemesService themesService) {
        this.moviesService = moviesService;
        this.actorsService = actorsService;
        this.crewService = crewService;
        this.genresService = genresService;
        this.postersService = postersService;
        this.releasesService = releasesService;
        this.studiosService = studiosService;
        this.themesService = themesService;
    }

    /**
     * Retrieves a paginated and filtered list of movies based on provided sorting, categories, and order.
     *
     * @return a list of {@link MoviesCardInfoDTO} objects representing movies matching the search criteria.
     */
    @GetMapping("/options")
    public Page<MoviesCardInfoDTO> getMovies(@RequestParam(required = false, defaultValue = "popular") String sort,
                                             @RequestParam(required = false, defaultValue = "DSC") String order,
                                             @RequestParam(name = "genre", required = false) String genre,
                                             @RequestParam(required = false) String year,
                                             @RequestParam(required = false, defaultValue = "0") String page,
                                             @RequestParam(required = false, defaultValue = "12") String size) {

        String genreFilter = ("all".equals(genre)) ? null : genre;
        String yearFilter = ("all".equals(year)) ? null : year;
        // Passa l'anno al service
        return moviesService.getMoviesByOptions(sort, genreFilter, yearFilter, order, Integer.parseInt(page), Integer.parseInt(size));
    }

    /**
     * Retrieves a paginated list of movies matching the specified title.
     *
     * @param filmTitle the title of the movie to search for
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies matching the specified title
     */
    @GetMapping("/name/{filmTitle}")
    public ResponseEntity<Page<MoviesCardInfoDTO>> getTitle(@PathVariable String filmTitle,
                                                              @RequestParam(defaultValue = "0") Integer page,
                                                              @RequestParam(defaultValue = "12") Integer size ) {
        if (filmTitle == null || filmTitle.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Page<MoviesCardInfoDTO> movie = moviesService.getMovieByTitle(filmTitle, page, size);
        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Retrieves a paginated list of movies belonging to a specific genre.
     *
     * @param genre the genre to filter movies by
     * @param page  the page number to retrieve (zero-based)
     * @param size  the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies in the specified genre
     */
    @GetMapping("/genre/{genre}")
    public ResponseEntity<Page<MoviesCardInfoDTO>> getGenre (@PathVariable String genre,
                                                             @RequestParam(defaultValue = "0") Integer page,
                                                             @RequestParam(defaultValue = "12") Integer size) {
        if (genre == null || genre.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Page<MoviesCardInfoDTO> movie = moviesService.getMoviesByGenre(genre, page, size);
        /*if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        } */

        List<MoviesCardInfoDTO> list = movie.getContent();
        for (MoviesCardInfoDTO card : list) {
            //System.out.println(card.getRating());
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Retrieves a paginated list of movies with the specified rating.
     *
     * @param rating the rating to filter movies
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesInfoDTO} objects for movies with the specified rating
     */
    @GetMapping("/rating/{rating}")
    public ResponseEntity<Page<MoviesCardInfoDTO>> getRating (@PathVariable double rating,
                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                 @RequestParam(defaultValue = "12") Integer size ) {
        if (rating < 0 || rating > 10) {
            return ResponseEntity.badRequest().build();
        }
        Page<MoviesCardInfoDTO> movie = moviesService.getMoviesByRating(rating, page, size);
        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Retrieves a paginated list of movies released in a specific year.
     *
     * @param year the year of release to filter movies
     * @param page the page number to retrieve (zero-based)
     * @param size the number of results per page
     * @return a list of {@link MoviesCardInfoDTO} objects for movies released in the specified year
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<Page<MoviesCardInfoDTO>> getYear (@PathVariable String year,
                                                               @RequestParam(defaultValue = "0") Integer page,
                                                               @RequestParam(defaultValue = "12") Integer size) {
        if (year == null || year.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Page<MoviesCardInfoDTO> movie = moviesService.getMoviesByYear(year, page, size);
        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Retrieves a list of movies featuring a specific Oscar-winning actor.
     *
     * @param actor the name of the Oscar-winning actor
     * @return a list of {@link MoviesCardInfoDTO} objects for movies featuring the specified actor
     */
    @GetMapping("/actor/{actor}")
    public ResponseEntity<List<MoviesCardInfoDTO>> getOscarActor (@PathVariable String actor) {
        if (actor == null || actor.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<MoviesCardInfoDTO> movie = moviesService.getMoviesByActorOscarWinner(actor);
        if (movie.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * Adds a new movie to the system using provided information.
     *
     * @param movie the Movie entity to be added.
     */
    @PostMapping("/add")
    public void addMovie(@RequestBody Movies movie) {
        moviesService.addMovie(movie);
    }

    /**
     * Retrieves a list of related movie associated with a specific movie.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link MoviesCardInfoDTO} objects representing the related movies
     */
    @GetMapping("/related")
    public List<MoviesCardInfoDTO> getRelatedMovies(@RequestParam String movieId,
                                                     @RequestParam(defaultValue = "0") String page,
                                                     @RequestParam(defaultValue = "6") String size) {
        return moviesService.getRelatedMoviesByThemes(movieId, Integer.parseInt(page), Integer.parseInt(size));
    }




   @GetMapping("/{movieId}")
    public ResponseEntity<Page<MoviesCardInfoDTO>> getMovieById(@PathVariable String movieId) {
        Page<MoviesCardInfoDTO> movie = moviesService.getMoviesDetail(movieId);

        List<MoviesCardInfoDTO> test = movie.getContent();
       System.out.println("quanto il test è lungo "+ test.size() );
        for (MoviesCardInfoDTO e : test){
            System.out.println("risultato test: "+ e );
        }

        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    //prende gli anni dei film
    @GetMapping("/years")
    public ResponseEntity<List<String>> getAllYears() {
        List<String> years = moviesService.getAllYears();
        return ResponseEntity.ok(years);
    }
}