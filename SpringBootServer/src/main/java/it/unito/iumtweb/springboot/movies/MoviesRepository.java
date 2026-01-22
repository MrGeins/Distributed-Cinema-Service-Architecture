package it.unito.iumtweb.springboot.movies;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Movies} entities.
 * Provides various methods for querying movies based on different criteria.
 */
@Repository
public interface MoviesRepository extends JpaRepository<Movies, Long> {

    /**
     * Retrieves a movie by its unique business identifier.
     *
     * @param movieId the business identifier of the movie
     * @return the {@link Movies} entity matching the given movieId, or null if not found
     */
    Movies findByMovieId(String movieId);

    /**
     * Retrieves the top 20 movies ordered by rating in descending order.
     *
     * @return a list of the top 20 {@link Movies} entities by rating
     */
    @Query(nativeQuery = true, value = "SELECT * FROM Movies ORDER BY rating DESC LIMIT 20")
    List<Movies> findTop20MoviesByOrderByRatingDesc();

    /**
     * Retrieves a paginated list of movies featuring the specified actor, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param name the name of the actor (case-insensitive)
     * @return a list of {@link MoviesCardInfoDTO} objects for movies featuring the actor
     */
    @Query(nativeQuery = true, value = "SELECT DISTINCT m.movie_id AS movieId, m.name AS movieTitle, m.tagline AS tagline, m.description AS description, " +
            "m.rating AS rating, m.date AS yearOfRelease, p.link AS posterLink " +
            "FROM Movies as m JOIN Actors as a ON a.movie_id = m.movie_id LEFT JOIN Posters as p ON m.movie_id = p.movie_id " +
            "WHERE LOWER(a.name) = LOWER(:name)",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies as m JOIN Actors as a ON a.movie_id = m.movie_id LEFT JOIN Posters as p ON m.movie_id = p.movie_id " +
                    "WHERE LOWER(a.name) = LOWER(:name)")
    Page<MoviesCardInfoDTO> findMoviesByActor(@Param("name") String name, Pageable pageable);

    /**
     * Retrieves a paginated list of movies whose titles contain the specified string, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param title    the partial or full title to search for (case-insensitive)
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects matching the title
     */
    @Query(nativeQuery = true,
            value = "SELECT m.movie_id as movieId, " +
                    "m.name as movieTitle, " +
                    "m.tagline as tagline, " +
                    "m.description as description, " +
                    "CAST(m.rating AS TEXT) as rating, " +
                    "CAST(m.date AS TEXT) as yearOfRelease, " +
                    "p.link as posterLink, " +
                    "COALESCE(STRING_AGG(DISTINCT a.name, ', '), 'N/A') as roles, " +
                    "EXISTS(SELECT 1 FROM the_oscar_awards oa WHERE LOWER(oa.film) = LOWER(m.name) AND oa.winner = 'true') as oscarWinner " +
                    "FROM Movies m " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "LEFT JOIN Actors a ON m.movie_id = a.movie_id " +
                    "WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :title, '%')) " +
                    "GROUP BY m.movie_id, m.name, m.tagline, m.description, m.rating, m.date, p.link",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies m " +
                    "WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :title, '%')) ")
    Page<MoviesCardInfoDTO> findMoviesByTitle(@Param("title") String title, Pageable pageable);

    /**
     * Retrieves a paginated list of movies filtered by a specified genre, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param genre the genre of the movie
     * @return a list of {@link MoviesCardInfoDTO} objects for movies filtered by a genre
     */
    /*@Query(nativeQuery = true, value = "SELECT DISTINCT m.movie_id AS movieId, m.name AS movieTitle, m.tagline AS tagline, m.description AS description, " +
            "m.rating AS rating, m.date AS yearOfRelease, p.link AS posterLink " +
            "FROM Movies m JOIN Genres g ON m.movie_id = g.movie_id LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
            "WHERE g.genre = :genre ORDER BY m.rating",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies m JOIN Genres g ON m.movie_id = g.movie_id LEFT JOIN Posters p ON m.movie_id = p.movie_id WHERE g.genre = :genre") */
    @Query(nativeQuery = true,
            value = "SELECT m.movie_id as movieId, m.name as movieTitle, m.tagline as tagline, m.description as description, " +
                    "m.rating as rating, m.date as yearOfRelease, p.link as posterLink, " +
                    "STRING_AGG(a.name, ', ') as roles, " +
                    "EXISTS(SELECT 1 FROM the_oscar_awards oa WHERE oa.film = m.name AND oa.winner = true) as oscarWinner " +
                    "FROM Movies m " +
                    "JOIN Genres g ON m.movie_id = g.movie_id " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "LEFT JOIN Actors a ON m.movie_id = a.movie_id " +
                    "WHERE g.genre = :genre " +
                    "GROUP BY m.movie_id, m.name, m.tagline, m.description, m.rating, m.date, p.link",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies m " +
                    "JOIN Genres g ON m.movie_id = g.movie_id " +
                    "WHERE g.genre = :genre")
    Page<MoviesCardInfoDTO> findMoviesByGenre(@Param("genre") String genre, Pageable pageable);

    /**
     * Retrieves a paginated list of movies released in the specified year, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param date     the year of release
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects released in the specified year
     */
    @Query(nativeQuery = true, value =
            "SELECT DISTINCT ON (m.movie_id) " +
                    "  m.movie_id AS movieId, " +          // 1. String
                    "  m.name AS movieTitle, " +           // 2. String
                    "  m.tagline AS tagline, " +           // 3. String
                    "  m.description AS description, " +   // 4. String
                    "  CAST(m.rating AS VARCHAR) AS rating, " + // 5. String (Cast necessario perché il DTO vuole String)
                    "  m.date AS yearOfRelease, " +        // 6. String
                    "  p.link AS posterLink, " +           // 7. String
                    "  '' AS roles, " +                    // 8. String (Segnaposto vuoto per i ruoli)
                    "  COALESCE(o.winner, false) AS oscarWinner " + // 9. Boolean
                    "FROM Movies m " +
                    "JOIN Posters p ON m.movie_id = p.movie_id " +
                    "LEFT JOIN the_oscar_awards o ON LOWER(o.film) = LOWER(m.name) " +
                    "WHERE m.date = :date " +
                    "ORDER BY m.movie_id, m.rating DESC",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies m WHERE m.date = :date")
    Page<MoviesCardInfoDTO> findMoviesByDate(String date, Pageable pageable);

    /**
     * Retrieves a paginated list of movies with the specified rating, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param rating   the rating of the movie
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects with the specified rating
     */
    @Query(nativeQuery = true, value = "SELECT DISTINCT m.movie_id as movieId, m.name, m.tagline, m.description, m.rating, m.date as yearOfRelease, p.link as posterLink " +
            "FROM Movies as m JOIN Posters as p ON m.movie_id = p.movie_id " +
            "WHERE m.rating = :rating",
            countQuery = "select count(DISTINCT m.movie_id) FROM Movies as m JOIN Posters as p ON m.movie_id = p.movie_id WHERE m.rating = :rating")
    Page<MoviesCardInfoDTO> findMoviesByRating(Double rating, Pageable pageable);

    /**
     * Retrieves a paginated list of movies matching the specified genres, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param genres   a list of genres to filter by
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects matching the criteria
     */
    /*@Query(nativeQuery = true,value = "SELECT DISTINCT m.movie_id as movieId, m.name, m.tagline, m.description, m.rating, m.date as yearOfRelease, p.link as posterLink " +
            "FROM Movies as m JOIN Genres as g ON m.movie_id = g.movie_id JOIN Posters as p ON m.movie_id = p.movie_id " +
            "WHERE g.genre in :genres",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies as m JOIN Genres as g ON m.movie_id = g.movie_id JOIN Posters as p ON m.movie_id = p.movie_id WHERE g.genre in :genres") */
    @Query(nativeQuery = true, value =
            "SELECT m.movie_id as movieId, m.name as movieTitle, m.tagline as tagline, m.description as description, " +
                    "m.rating as rating, m.date as yearOfRelease, p.link as posterLink, " +
                    "STRING_AGG(DISTINCT a.name, ', ') as roles, " +
                    "EXISTS(SELECT 1 FROM the_oscar_awards oa WHERE oa.film = m.name AND oa.winner = true) as oscarWinner " +
                    "FROM Movies m " +
                    "JOIN Genres g ON m.movie_id = g.movie_id " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "LEFT JOIN Actors a ON m.movie_id = a.movie_id " +
                    "WHERE g.genre IN (:genres) " +
                    "AND (:year IS NULL OR m.date = :year) " +  // <--- ECCO IL FILTRO MANCANTE!
                    "GROUP BY m.movie_id, m.name, m.tagline, m.description, m.rating, m.date, p.link",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies m " +
                    "JOIN Genres g ON m.movie_id = g.movie_id " +
                    "WHERE g.genre IN (:genres) AND (:year IS NULL OR m.date = :year)")
    Page<MoviesCardInfoDTO> findMoviesByOptions(@Param("genres") List<String> genres, @Param("year") String year, Pageable pageable);

    /**
     * Retrieves a paginated list of movies filtered by the specified year, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param year     the year of release
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects released in the specified year
     */
    @Query(nativeQuery = true, value =
            "SELECT m.movie_id as movieId, m.name as movieTitle, m.tagline, m.description, " +
                    "m.rating, m.date as yearOfRelease, p.link as posterLink, " +
                    "'' as roles, false as oscarWinner " +
                    "FROM Movies m " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "WHERE (:year IS NULL OR m.date = :year) ",
            countQuery = "SELECT count(*) FROM Movies m WHERE (:year IS NULL OR m.date = :year)")
    Page<MoviesCardInfoDTO> findMoviesByOptionsNoGenres(@Param("year") String year, Pageable pageable);

    /**
     * Retrieves a paginated list of all movies (no genre filter), projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects
     */
    /*@Query(nativeQuery = true,value = "SELECT DISTINCT m.movie_id as movieId, m.name, m.tagline, m.description, m.rating, m.date as yearOfRelease, p.link as posterLink " +
            "FROM Movies as m JOIN Posters as p ON m.movie_id = p.movie_id",
            countQuery = "SELECT count(DISTINCT m.movie_id) FROM Movies as m JOIN Posters as p ON m.movie_id = p.movie_id")*/
    @Query(nativeQuery = true, value =
            "SELECT m.movie_id as movieId, m.name as movieTitle, m.tagline, m.description, " +
                    "m.rating, m.date as yearOfRelease, p.link as posterLink, " +
                    "'' as roles, false as oscarWinner " +
                    "FROM Movies m " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id ",
                    countQuery = "SELECT count(*) FROM Movies")
    Page<MoviesCardInfoDTO> findMoviesByOptionsNoGenres(Pageable pageable);

    /**
     * Retrieves a list of movies featuring actors who have won an Oscar, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param name the name of the Oscar-winning actor (case-insensitive)
     * @return a {@link List} of {@link MoviesCardInfoDTO} objects for movies featuring Oscar-winning actors
     */
    @Query(nativeQuery = true, value =
            "SELECT DISTINCT ON (m.movie_id) " +
                    "  m.movie_id AS movieId, " +
                    "  m.name AS movieTitle, " +
                    "  m.tagline AS tagline, " +
                    "  m.description AS description, " +
                    "  m.rating AS rating, " +
                    "  m.date AS yearOfRelease, " +
                    "  p.link AS posterLink, " +
                    "  o.category AS roles, " +
                    "  COALESCE(o.winner, false) AS oscarWinner " +
                    "FROM Movies m " +
                    "JOIN Actors a ON m.movie_id = a.movie_id " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "LEFT JOIN the_oscar_awards o ON LOWER(o.name) = LOWER(a.name) " +
                    "  AND LOWER(o.film) = LOWER(m.name) " +
                    "WHERE LOWER(a.name) = LOWER(:name) " +
                    "ORDER BY m.movie_id, o.winner DESC")
    List<MoviesCardInfoDTO> findMoviesByActorOscarWinner(@Param("name") String name);

    /**
     * Retrieves a paginated list of movies that share at least a minimum number of specified themes (excluding a given movie), projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param themes    a list of themes to match
     * @param movieId   the movie ID to exclude from results
     * @param minThemes the minimum number of matching themes required
     * @param pageable  the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} objects matching the criteria
     */
    @Query(nativeQuery = true,
            value = "SELECT m.movie_id AS movieId, " +
                    "       m.name AS movieTitle, " +
                    "       m.tagline AS tagline, " +
                    "       m.description AS description, " +
                    "       CAST(m.rating AS TEXT) AS rating, " +
                    "       CAST(m.date AS TEXT) AS yearOfRelease, " +
                    "       MAX(p.link) AS posterLink, " +
                    "       COALESCE(STRING_AGG(DISTINCT a.name, ', '), 'N/A') AS roles, " +
                    "       EXISTS(SELECT 1 FROM the_oscar_awards oa WHERE LOWER(oa.film) = LOWER(m.name) AND oa.winner = 'true') AS oscarWinner " +
                    "FROM Movies m " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "JOIN Themes t ON m.movie_id = t.movie_id " +
                    "LEFT JOIN Actors a ON m.movie_id = a.movie_id " +
                    "WHERE t.theme IN :themes AND m.movie_id != :movieId " +
                    "GROUP BY m.movie_id, m.name, m.tagline, m.description, m.rating, m.date " +
                    "HAVING COUNT(DISTINCT t.theme) >= :minThemes " +
                    "ORDER BY COUNT(DISTINCT t.theme) DESC, m.rating DESC",
            countQuery = "SELECT COUNT(DISTINCT m.movie_id) " +
                    "FROM Movies m " +
                    "JOIN Themes t ON m.movie_id = t.movie_id " +
                    "WHERE t.theme IN :themes AND m.movie_id != :movieId " +
                    "GROUP BY m.movie_id " +
                    "HAVING COUNT(DISTINCT t.theme) >= :minThemes")
    Page<MoviesCardInfoDTO> findMoviesWithMinimumThemes(@Param("themes") List<String> themes, @Param("movieId") String movieId, @Param("minThemes") int minThemes, Pageable pageable);



    /**
     * Retrieves detailed information about a movie by its unique business identifier, projecting results into {@link MoviesCardInfoDTO}.
     *
     * @param movieId  the business identifier of the movie
     * @param pageable the pagination information
     * @return a {@link Page} of {@link MoviesCardInfoDTO} containing detailed information about the movie
     */
    @Query(nativeQuery = true,
            value = "SELECT m.movie_id AS movieId, m.name AS movieTitle, m.tagline AS tagline, m.description AS description, " +
                    "m.rating AS rating, m.date AS yearOfRelease, p.link AS posterLink, " +
                    "STRING_AGG(DISTINCT a.name, ', ') AS roles, " +
                    "EXISTS(SELECT 1 FROM the_oscar_awards oa WHERE oa.film = m.name AND oa.winner = true) AS oscarWinner " +
                    "FROM Movies m " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "LEFT JOIN Actors a ON m.movie_id = a.movie_id " +
                    "WHERE m.movie_id = :movieId " +
                    "GROUP BY m.movie_id, m.name, m.tagline, m.description, m.rating, m.date, p.link",
            countQuery = "SELECT count(*) FROM Movies m WHERE m.movie_id = :movieId")
    Page<MoviesCardInfoDTO> findMovieDetailById(@Param("movieId") String movieId, Pageable pageable);

    /**
     * Retrieves a list of all distinct years in which movies were released, ordered in descending order.
     *
     * @return a list of distinct years as strings
     */
    @Query("SELECT DISTINCT m.date FROM Movies m WHERE m.date IS NOT NULL ORDER BY m.date DESC")
    List<String> findAllDistinctYears();



    @Query(nativeQuery = true, value =
            "SELECT m.movie_id as movieId, m.name as movieTitle, m.tagline, m.description, " +
                    "m.rating, m.date as yearOfRelease, p.link as posterLink, " +
                    "'' as roles, " +
                    "EXISTS(SELECT 1 FROM the_oscar_awards oa WHERE oa.film = m.name AND oa.winner = true) as oscarWinner " +
                    "FROM Movies m " +
                    "LEFT JOIN Posters p ON m.movie_id = p.movie_id " +
                    "WHERE (:year IS NULL OR m.date = :year) " +
                    "AND (:genres IS NULL OR EXISTS ( " +
                    "    SELECT 1 FROM Genres g WHERE g.movie_id = m.movie_id AND g.genre IN (:genres) " +
                    ")) " +
                    "GROUP BY m.movie_id, m.name, m.tagline, m.description, m.rating, m.date, p.link",
            countQuery = "SELECT count(*) FROM Movies m WHERE (:year IS NULL OR m.date = :year) " +
                    "AND (:genres IS NULL OR EXISTS (SELECT 1 FROM Genres g WHERE g.movie_id = m.movie_id AND g.genre IN (:genres)))")
    Page<MoviesCardInfoDTO> findMoviesWithSmartFilters(
            @Param("genres") String genres,
            @Param("year") String year,
            Pageable pageable
    );



}





