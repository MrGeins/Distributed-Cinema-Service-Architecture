package it.unito.iumtweb.springboot.studios;

import jakarta.persistence.*;

/**
 * Entity class representing a Studio record.
 */
@Entity
@Table(name = "studios")
public class Studios {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(name = "studio")
    private String studio;

    /**
     * Default constructor for Studios.
     */
    public Studios() {}

    /**
     * Constructs a new Studios entity with the specified studio name and movie ID.
     *
     * @param studio  the name of the studio
     * @param movieId the unique identifier of the movie
     */
    public Studios(String studio, String movieId) {
        this.studio = studio;
        this.movieId = movieId;
    }

    // Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

}