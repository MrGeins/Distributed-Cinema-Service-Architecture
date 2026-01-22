package it.unito.iumtweb.springboot.actors;
import jakarta.persistence.*;

/**
 * Entity class representing an Actor record.
 */
@Entity
@Table(name = "Actors")
public class Actors {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    private String name;
    @Column(nullable = false)
    private String role;

    /**
     * Default constructor for Actors.
     */
    public Actors() {}

    /**
     * Constructs a new Actors entity with the specified movie ID, name, and role.
     *
     * @param movieId the unique identifier of the movie
     * @param name    the name of the actor
     * @param role    the role played by the actor in the movie
     */
    public Actors(String movieId, String name, String role) {
        this.movieId = movieId;
        this.name = name;
        this.role = role;
    }

    // Getter & Setter
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}


