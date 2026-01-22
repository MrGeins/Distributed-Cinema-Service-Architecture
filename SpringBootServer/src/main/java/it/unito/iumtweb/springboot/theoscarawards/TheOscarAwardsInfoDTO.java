package it.unito.iumtweb.springboot.theoscarawards;

/**
 * Data Transfer Object (DTO) that represent The Oscar Awards information.
 * <p>
 *     This class encapsulates key details about Oscar nominations and winners,
 *     such as the year of the film, award category, nominee's name, associated film,
 *     and whether the nominee won the award. It facilitates efficient transfer of
 *     Oscar-related data between layers or over network requests in applications like REST APIs or UI components
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * TheOscarAwardsInfoDTO oscarInfo = new TheOscarAwardsInfoDTO(
 *     2020,
 *     "Best Picture",
 *     "Nominee Name",
 *     "Film Title",
 *     true
 * );
 * }
 * </pre>
 * </p>
 */
public class TheOscarAwardsInfoDTO {
    private Integer yearFilm;
    private String category;
    private String name;
    private String film;
    private Boolean winner;

    /**
     * Constructs a new TheOscarAwardsInfoDTO with the specified details.
     *
     * @param yearFilm the year of the film
     * @param category the award category
     * @param name     the name of the nominee
     * @param film     the film associated with the nomination
     * @param winner   indicates if the nominee won the award
     */
    public TheOscarAwardsInfoDTO(Integer yearFilm, String category, String name, String film, Boolean winner) {
        this.yearFilm = yearFilm;
        this.category = category;
        this.name = name;
        this.film = film;
        this.winner = winner;
    }

    // Getter & Setter
    public Integer getYearFilm() {
        return yearFilm;
    }

    public void setYearFilm(Integer yearFilm) {
        this.yearFilm = yearFilm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilm() {
        return film;
    }

    public void setFilm(String film) {
        this.film = film;
    }

    public Boolean isWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}
