package it.unito.iumtweb.springboot.theoscarawards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link TheOscarAwards} entities.
 * Provides methods for querying oscar awards with support of lists and custom queries.
 */
@Repository
public interface TheOscarAwardsRepository extends JpaRepository<TheOscarAwards, Long> {
    /**
     * Counts the number of Oscar awards for a specific name and category.
     *
     * @param name the name of the nominee
     * @param category the category of the award
     * @return the number of Oscar awards matching the criteria
     */
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM the_oscar_awards WHERE name = ?1 AND category = ?2")
    int countTheOscarAwardsByNameAndCategory(String name, String category);




    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) FROM the_oscar_awards " +
                    "WHERE LOWER(name) = LOWER(?1) " +
                    "AND category LIKE 'ACTOR%' " + // Cattura tutte le varianti di "ACTOR IN A..."
                    "AND winner = true") // Conta solo le vittorie (t)
    int countOscarsByActorGenericCategory(String name);
    /**
     * Retrieves all Oscar award records that belong to the specified category.
     *
     * @param category the award category
     * @return a list of {@link TheOscarAwards} entities belonging to the given category
     */
    List<TheOscarAwards> findByCategory(String category);

    /**
     * Retrieves all Oscar award records associated with a specific film year.
     *
     * @param yearFilm the release year of the films
     * @return a list of {@link TheOscarAwards} entities for films released in the given year
     */
    List<TheOscarAwards> findByYearFilm(int yearFilm);

    /**
     * Retrieves if the specified film won an Oscar in that year for "FILM" category.
     *
     * @param film the title of the film
     * @param yearCeremony the year of the ceremony
     * @return true if the film won an Oscar in that year, false otherwise
     */
    @Query(nativeQuery = true, value = "SELECT o.winner = TRUE FROM the_oscar_awards o WHERE o.film_title = :film AND o.year_ceremony = :yearCeremony AND o.category = 'FILM' LIMIT 1")
    boolean findWinnerByFilmAndYear(String film, int yearCeremony);

    /**
     * Retrieves if the specified actor won an Oscar for the given movie in the "ACTOR" category.
     *
     * @param name the name of the actor
     * @param filmTitle the title of the movie
     * @return true if the actor won an Oscar for that movie, false otherwise
     */
    @Query(nativeQuery = true, value = "SELECT o.winner = TRUE FROM the_oscar_awards o WHERE o.name = :name AND o.film_title = :filmTitle AND o.category = 'ACTOR' LIMIT 1")
    boolean findWinnerByActorName(String name, String filmTitle);

    /**
     * Finds Oscar awards where the nominee is a winner.
     *
     * @return a list of Oscar awards where the nominee won
     */
    List<TheOscarAwards> findByWinnerTrue();

    /**
     * Finds Oscar awards where the nominee is not a winner.
     *
     * @return a list of Oscar awards where the nominee did not win
     */
    List<TheOscarAwards> findByWinnerFalse();
}
