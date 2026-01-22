package it.unito.iumtweb.springboot.movies;

import jakarta.persistence.*;

/**
 * Entity class representing a Movie record.
 */
@Entity
@Table(name = "movies")
public class Movies {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String name;
    @Column(columnDefinition = "TEXT")
    private String date;
    @Column(columnDefinition = "TEXT")
    private String tagline;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = true)
    private Integer minute;
    @Column(nullable = true, columnDefinition = "TEXT")
    private String rating;

    /**
     * Default constructor for Movies.
     */
    public Movies() {}

    /**
     * Constructs a new Movies entity with the specified details.
     *
     * @param movieId     the unique identifier of the movie
     * @param name        the name of the movie
     * @param date        the release date of the movie
     * @param tagline     the tagline of the movie
     * @param description the description of the movie
     * @param minute      the duration of the movie in minutes
     * @param rating      the rating of the movie
     */
    public Movies(String movieId, String name, String date, String tagline, String description, Integer minute, String rating) {
        this.movieId = movieId;
        this.name = name;
        this.date = date;
        this.tagline = tagline;
        this.description = description;
        this.minute = (minute == null) ? 0 : minute;
        this.rating = (rating == null) ? "0.0" : rating;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Checks if the movie entity is present (i.e., has a non-null ID).
     * Made it for the tests
     *
     * @return true if the movie has a non-null ID, false otherwise.
     */
    public boolean isPresent() {
        return this.id != null;
    }
}
