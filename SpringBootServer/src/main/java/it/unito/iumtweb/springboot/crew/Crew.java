package it.unito.iumtweb.springboot.crew;

import jakarta.persistence.*;

/**
 * Entity representing a crew member associated with a movie.
 * <p>
 * This class is mapped to the "crew" table in the database and contains
 * information about the crew member's role and name, along with the
 * associated movie ID.
 * </p>
 */
@Entity
@Table(name = "crew")
public class Crew {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieId;
    @Column(columnDefinition = "TEXT")
    private String role;
    @Column(columnDefinition = "TEXT")
    private String name;

    /**
     * Default constructor required by JPA.
     */
    public Crew() {}

    /**
     * Constructs a new Crew member with the specified movie ID, role, and name.
     *
     * @param movieId the ID of the associated movie
     * @param role    the role of the crew member
     * @param name    the name of the crew member
     */
    public Crew(String movieId, String role, String name) {
        this.movieId = movieId;
        this.role = role;
        this.name = name;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
