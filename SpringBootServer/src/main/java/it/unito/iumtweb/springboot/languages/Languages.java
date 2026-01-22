package it.unito.iumtweb.springboot.languages;

import jakarta.persistence.*;

/**
 * Entity class representing a Language record.
 */
@Entity
@Table(name = "languages")
public class Languages {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(name = "language")
    private String language;

    /**
     * Default constructor for Languages.
     */
    public Languages() {}

    /**
     * Constructs a new Languages entity with the specified movie ID and language.
     *
     * @param movieId  the unique identifier of the movie
     * @param language the language of the movie
     */
    public Languages(String movieId, String language) {
     this.movieId = movieId;
        this.language = language;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
