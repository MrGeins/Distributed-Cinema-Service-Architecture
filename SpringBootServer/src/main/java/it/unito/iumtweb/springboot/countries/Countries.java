package it.unito.iumtweb.springboot.countries;

import jakarta.persistence.*;

/**
 * Entity class representing a Countries record.
 */
@Entity
@Table(name = "Countries")
public class Countries {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String country;

    /**
     * Default constructor for Countries.
     */
    public Countries() {}

    /**
     * Constructs a new Countries entity with the specified movie ID and country name.
     *
     * @param movieId the unique identifier of the movie
     * @param country the name of the country
     */
    public Countries(String movieId, String country) {
        this.movieId = movieId;
        this.country = country;
    }

    //Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
