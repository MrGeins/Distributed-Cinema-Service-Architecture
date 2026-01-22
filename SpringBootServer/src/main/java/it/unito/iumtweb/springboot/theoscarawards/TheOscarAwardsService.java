package it.unito.iumtweb.springboot.theoscarawards;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Oscar awards data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class TheOscarAwardsService {
    private final TheOscarAwardsRepository theOscarAwardsRepository;

    public TheOscarAwardsService(TheOscarAwardsRepository theOscarAwardsRepository) {
        this.theOscarAwardsRepository = theOscarAwardsRepository;
    }

    /**
     * Counts how many Oscar awards a specific person received in a given category.
     *
     * @param name the name of the nominee
     * @param category the award category
     * @return the number of awards matching the given criteria
     */
    public int countAwards(String name, String category) {
        return theOscarAwardsRepository.countTheOscarAwardsByNameAndCategory(name, category);
    }

    /**
     * Retrieves all Oscar awards belonging to the specified category
     *
     * @param category the award category
     * @return a list of {@link TheOscarAwardsInfoDTO} objects representing the awards in that category
     */
    public List<TheOscarAwardsInfoDTO> getAwardsByCategory(String category) {
        return theOscarAwardsRepository.findByCategory(category)
                .stream().map(this::toDTO).toList();
    }

    /**
     * Retrieves all Oscar awards for films released in a specific year
     *
     * @param yearFilm the film release year
     * @return a list of {@link TheOscarAwardsInfoDTO} objects representing the awards in that year
     */
    public List<TheOscarAwardsInfoDTO> getAwardsByYearFilm(int yearFilm) {
        return theOscarAwardsRepository.findByYearFilm(yearFilm)
                .stream().map(this::toDTO).toList();
    }

    /**
     * Checks whether a specific film won the Oscar for Best Picture ("FILM" category)
     * in a given ceremony year.
     *
     * @param filmTitle the title of the film
     * @param yearCeremony the year of the Oscar ceremony
     * @return {@code true} if the film won Best Picture that year, {@code false} otherwise
     */
    public boolean didFilmWinBestPicture(String filmTitle, int yearCeremony) {
        return theOscarAwardsRepository.findWinnerByFilmAndYear(filmTitle, yearCeremony);
    }

    /**
     * Checks whether a specific actor won an Oscar for their role in a particular film.
     *
     * @param actorName the name of the actor
     * @param filmTitle the title of the film
     * @return {@code true} if the actor won an Oscar for that movie, {@code false} otherwise
     */
    public boolean didActorWinForMovie(String actorName, String filmTitle) {
        return theOscarAwardsRepository.findWinnerByActorName(actorName, filmTitle);
    }

    /**
     * Retrieves all Oscar awards where the nominee is a winner
     *
     * @return a list of {@link TheOscarAwardsInfoDTO} objects representing all winning awards
     */
    public List<TheOscarAwardsInfoDTO> getWinners() {
        return theOscarAwardsRepository.findByWinnerTrue()
                .stream().map(this::toDTO).toList();
    }

    /**
     * Retrieves all Oscar nominations where the nominee did not win and converts them to DTOs.
     *
     * @return a list of {@link TheOscarAwardsInfoDTO} objects representing non-winning nominations
     */
    public List<TheOscarAwardsInfoDTO> getNotWinnerNominations () {
        return theOscarAwardsRepository.findByWinnerFalse()
                .stream().map(this::toDTO).toList();
    }

    /**
     * Converts a {@link TheOscarAwards} entity into a corresponding DTO.
     *
     * @param award the award entity to convert
     * @return a DTO representation of the given entity
     */
    private TheOscarAwardsInfoDTO toDTO(TheOscarAwards award) {
        return new TheOscarAwardsInfoDTO(
                award.getYearFilm(),
                award.getCategory(),
                award.getActorName(),
                award.getFilmTitle(),
                award.getWinner()
        );
    }

}
