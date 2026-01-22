package it.unito.iumtweb.springboot.releases;

import jakarta.persistence.*;

/**
 * Entity class representing a Release record.
 */
@Entity
@Table(name="Releases")
public class Releases {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String country;
    @Column(columnDefinition = "TEXT")
    private String date;
    @Column(columnDefinition = "TEXT")
    private String type;
    @Column(columnDefinition = "TEXT")
    private String rating;

    /**
     * Default constructor for Releases.
     */
    public Releases() {}

    /**
     * Constructor for Releases with the specified details.
     *
     * @param movieId the unique identifier of the movie
     * @param country the country of release
     * @param date    the release date
     * @param type    the type of release
     * @param rating  the rating of the release
     */
    public Releases(String movieId, String country, String date, String type, String rating) {
        this.movieId = movieId;
        this.country = country;
        this.date = date;
        this.type = type;
        this.rating = rating;
    }

    // Getter & Setter
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
