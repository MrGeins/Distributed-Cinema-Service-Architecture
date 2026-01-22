package it.unito.iumtweb.springboot.themes;

import jakarta.persistence.*;

/**
 * Entity class representing a Theme record.
 */
@Entity
@Table(name = "themes")
public class Themes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String theme;

    /**
     * Default constructor for Themes.
     */
    public Themes() {}

    /**
     * Constructor a Themes with specified theme and movieId.
     * @param movieId the unique identifier of the movie
     * @param theme the theme associated with the movie
     */
    public Themes(String movieId, String theme){
        this.movieId = movieId;
        this.theme = theme;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

}