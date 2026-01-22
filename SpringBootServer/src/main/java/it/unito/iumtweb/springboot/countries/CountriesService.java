package it.unito.iumtweb.springboot.countries;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Countries data. This class acts as an intermediate layer between the controller
 * and the repository.
 */
@Service
public class CountriesService {
    private final CountriesRepository repo;

    /**
     * Constructs a new CountriesService with the specified CountriesRepository.
     *
     * @param repo the repository for country entities
     */
    public CountriesService(CountriesRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves a list of countries associated with the specified movie ID.
     *
     * @param movieId the unique identifier of the movie
     * @return a list of {@link Countries} entities for the given movie
     */
    public List<Countries> getCountriesByMovieId(String movieId) {
        return repo.findByMovieId(movieId);
    }

    /**
     * Retrieves a list of distinct country names from the Countries entities.
     *
     * @return a list of unique country names
     */
    public List<String> getUniqueCountryNames() {
        return repo.findDistinctCountryNames();
    }

    /**
     * Counts the number of distinct movies associated with the specified country name.
     *
     * @param countryName the name of the country
     * @return the count of distinct movies for that country
     */
    public Long countMoviesPerCountry(String countryName) {
        return repo.countMoviesPerCountry(countryName);
    }
}
