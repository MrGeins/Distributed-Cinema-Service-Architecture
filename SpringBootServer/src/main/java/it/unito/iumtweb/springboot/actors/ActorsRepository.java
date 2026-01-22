package it.unito.iumtweb.springboot.actors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Actors} entities.
 * Provides methods for querying actors by name, movie ID, and roles.
 */
@Repository
public interface ActorsRepository extends JpaRepository<Actors, Long> {
    /**
     * Finds a list of actors by their exact name, ignoring case.
     *
     * @param name the name of the actor to search for
     * @return a list of {@link Actors} entities matching the specified name
     */
    List<Actors> findByNameIgnoreCase(String name);

    /**
     * Finds a list of actors whose names contain the specified substring, ignoring case.
     *
     * @param name the substring to search for in actor names
     * @return a list of {@link Actors} entities whose names match the search criteria
     */
    @Query("SELECT DISTINCT a.name FROM Actors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<String> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    /**
     * Finds a paginated list of actors associated with a specific movie ID.
     *
     * @param movieId  the unique identifier of the movie
     * @param pageable the pagination information
     * @return a paginated list of {@link Actors} entities for the given movie
     */
    @Query(nativeQuery = true, value = "SELECT DISTINCT * " +
            "FROM Actors as a " +
            "WHERE a.movie_id = :movieId",
            countQuery = "SELECT count(DISTINCT a.name) FROM Actors as a WHERE a.movie_id = :movieId")
    Page<Actors> findActorByMovieId(String movieId, Pageable pageable);

    /**
     * Finds a paginated list of distinct actor names that match the given search value.
     *
     * @param searchValue the string to search for in actor names
     * @param pageable    the pagination information
     * @return a paginated list of actor names matching the search criteria
     */
    @Query(nativeQuery = true, value="SELECT DISTINCT  a.name " +
            "FROM Actors as a " +
            "WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :searchValue, '%')) ",
            countQuery="SELECT count(DISTINCT  a.name) FROM Actors as a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    Page<String> findActorNameByString(@Param("searchValue")String searchValue, Pageable pageable);

    /**
     * Finds a list of roles played by an actor with the specified name.
     *
     * @param name the name of the actor
     * @return a list of roles played by the actor
     */
    @Query(nativeQuery = true, value="SELECT a.role FROM Actors a WHERE a.name = :name")
    List<String> findRolesByActorName(String name);


}

