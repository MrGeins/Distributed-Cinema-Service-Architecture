package it.unito.iumtweb.springboot.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Countries} entities.
 * Provides methods for querying countries by various criteria.
 */
@Repository
public interface CountriesRepository extends JpaRepository<Countries, Long> {

    /**
     * Retrieves a list of countries associated with the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Countries} entities for the given movie
     */
    List<Countries> findByMovieId(String movieId);

    /**
     * Retrieves a list of distinct country names from the Countries entities.
     *
     * @return a list of unique country names
     */
    @Query("SELECT DISTINCT c.country FROM Countries c WHERE c.movieId = :movieId")
    List<String> findDistinctCountryNames();

    /**
     * Counts the number of distinct movies associated with the specified country name.
     *
     * @param countryName the name of the country
     * @return the count of distinct movies for that country
     */
    @Query("SELECT COUNT(DISTINCT c.country) FROM Countries c WHERE c.country = :countryName")
    Long countMoviesPerCountry(@Param("countryName") String countryName);
}
