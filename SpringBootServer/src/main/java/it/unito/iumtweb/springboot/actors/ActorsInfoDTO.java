package it.unito.iumtweb.springboot.actors;

import it.unito.iumtweb.springboot.movies.MoviesCardInfoDTO;

import java.util.List;

/**
 * Data Transfer Object (DTO) that represents summary information about an actor,
 * including their name, a list of movies they have acted in, and the number of Oscars won.
 * <p>
 * This DTO includes the actor's name, a list of movies in which they appeared,
 *  and the total number of Oscars they have won. It provides the essential
 *  information typically needed for displaying actor profiles, listings, or
 *  search results in REST API responses or user interface components.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * ActorsInfoDTO actorInfo = new ActorsInfoDTO(
 *     "John Doe",
 *     movies,
 *     3
 * );
 * }
 * </pre>
 * </p>
 */
public class ActorsInfoDTO {
    private String name;
    private List<MoviesCardInfoDTO> movies;
    private Integer oscarNumber;

    /**
     * Constructs an {@code ActorsInfoDTO} with the specified details.
     *
     * @param name        the name of the actor
     * @param movies      a list of {@link MoviesCardInfoDTO} representing movies the actor has acted in
     * @param oscarNumber the number of Oscars won by the actor
     */
    public ActorsInfoDTO(String name, List<MoviesCardInfoDTO> movies, Integer oscarNumber) {
        this.name = name;
        this.movies = movies;
        this.oscarNumber = oscarNumber;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MoviesCardInfoDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MoviesCardInfoDTO> movies) {
        this.movies = movies;
    }

    public Integer getOscarNumber() {
        return oscarNumber;
    }

    public void setOscarNumber(Integer oscarNumber) {
        this.oscarNumber = oscarNumber;
    }
}
