package it.unito.iumtweb.springboot.genres;

import jakarta.persistence.*;

/**
 * Entity class representing a Genre record.
 */
@Entity
@Table(name = "Genres")
public class Genres {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String genre;

    /**
     * Default constructor for Genres.
     */
    public Genres() {}

    /**
     * Constructs a new Genres entity with the specified movie ID and genre.
     *
     * @param movieId the unique identifier of the movie
     * @param genre   the genre of the movie
     */
    public Genres(String movieId, String genre) {
     this.movieId = movieId;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
