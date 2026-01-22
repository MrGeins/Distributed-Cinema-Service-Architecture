package it.unito.iumtweb.springboot.posters;

import jakarta.persistence.*;

/**
 * Entity class representing a Posters record.
 */
@Entity
@Table(name = "Posters")
public class Posters {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String link;

    /**
     * Default constructor for Posters.
     */
    public Posters() {}

    /**
     * Constructs a new Posters entity with the specified movie ID and link.
     *
     * @param movieId the unique identifier of the movie
     * @param link    the link to the poster image
     */
    public Posters(String movieId, String link) {
        this.movieId = movieId;
        this.link = link;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }



    //Getter and Setter
}
