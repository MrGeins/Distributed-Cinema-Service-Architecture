package it.unito.iumtweb.springboot.theoscarawards;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes endpoints for querying Oscar awards data.
 * It provides access to winners, nominations, counts, and award details
 * filtered by category, year, film, or actor.
 * All endpoints are mapped under <code>/the_oscar_awards</code>.
 */
@RestController
@RequestMapping("/the_oscar_awards")
public class TheOscarAwardsController {
    private final TheOscarAwardsService service;

    /**
     * Constructs a new TheOscarAwardsController with the specified TheOscarAwardsService.
     *
     * @param service the service for Oscar awards data
     */
    public TheOscarAwardsController(TheOscarAwardsService service) {
        this.service = service;
    }

    /**
     * Retrieves all Oscar-winning entries.
     *
     * @return a list of {@link TheOscarAwardsInfoDTO} objects representing winners
     */
    @GetMapping("/winners")
    public ResponseEntity<List<TheOscarAwardsInfoDTO>> getWinners() {
        return ResponseEntity.ok(service.getWinners());
    }

    /**
     * Retrieves all Oscar nominations that did not win.
     *
     * @return a list of {@link TheOscarAwardsInfoDTO} objects representing non-winning nominations
     */
    @GetMapping("/notwinners")
    public ResponseEntity<List<TheOscarAwardsInfoDTO>> getNotWinners() {
        return ResponseEntity.ok(service.getNotWinnerNominations());
    }

    /**
     * Retrieves all awards belonging to a specific category.
     *
     * @param category the award category
     * @return a list of {@link TheOscarAwardsInfoDTO} objects for that category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TheOscarAwardsInfoDTO>> getAwardsByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(service.getAwardsByCategory(category));
    }

    /**
     * Retrieves a paginated list of movies belonging to a specific genre.
     *
     * @param year the film's release year
     * @return a list of {@link TheOscarAwardsInfoDTO} objects for that year
     */
    @GetMapping("/year/{year}")
    public ResponseEntity<List<TheOscarAwardsInfoDTO>> getAwardsByYear(
            @PathVariable int year) {
        return ResponseEntity.ok(service.getAwardsByYearFilm(year));
    }

    /**
     * Counts how many awards a person received in a given category.
     *
     * @param name the name of the nominee
     * @param category the category of the award
     * @return the number of matching awards
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> countAwards(
            @RequestParam String name,
            @RequestParam String category) {
        return ResponseEntity.ok(service.countAwards(name, category));
    }

    /**
     * Checks whether a film won Best Picture ("FILM" category) in a given ceremony year.
     *
     * @param filmTitle the title of the film
     * @param year the ceremony year
     * @return {@code true} if the film won Best Picture, {@code false} otherwise
     */
    @GetMapping("/film/winner")
    public ResponseEntity<Boolean> didFilmWin(
            @RequestParam String filmTitle,
            @RequestParam int year) {
        return ResponseEntity.ok(service.didFilmWinBestPicture(filmTitle, year));
    }

    /**
     * Checks whether an actor won an Oscar for a specific film.
     *
     * @param name the actor's name
     * @param film the film title
     * @return {@code true} if the actor won for that film, {@code false} otherwise
     */
    @GetMapping("/actor/winner")
    public ResponseEntity<Boolean> didActorWinForMovie(
            @RequestParam String name,
            @RequestParam String film) {
        return ResponseEntity.ok(service.didActorWinForMovie(name, film));
    }
}

