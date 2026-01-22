package it.unito.iumtweb.springboot.crew;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing crew information for a movie.
 * <p>
 * This class encapsulates the main crew roles such as directors, producers,
 * and screenwriters, along with other crew credits categorized by their roles. It facilitates
 * efficient transfer of crew data between layers or over network requests in applications like REST APIs or UI components.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * CrewInfoDTO crewInfo = new CrewInfoDTO(
 *     List.of("Director Name"),
 *     List.of("Producer Name"),
 *     List.of("Screenwriter Name"),
 *     List.of(new CrewInfoDTO.CrewCreditDTO("Cinematographer", List.of("Cinematographer Name")))
 * );
 * }
 * </pre>
 * </p>
 */
public class CrewInfoDTO {
    private List<String> directors;
    private List<String> producers;
    private List<String> screenwriters;
    // Other crew credits categorized by their roles
    private List<CrewCreditDTO> otherCredits;

    /**
     * Constructs a {@code CrewInfoDTO} with the specified crew details.
     *
     * @param directors      a list of director names
     * @param producers      a list of producer names
     * @param screenwriters  a list of screenwriter names
     * @param otherCredits   a list of other crew credits categorized by their roles
     */
    public CrewInfoDTO(List<String> directors, List<String> producers, List<String> screenwriters, List<CrewCreditDTO> otherCredits) {
        this.directors = directors;
        this.producers = producers;
        this.screenwriters = screenwriters;
        this.otherCredits = otherCredits;
    }

    /**
     * Represents a specific crew credit associated with a movie.
     * <p>
     * Each credit contains a crew role (e.g., "Cinematographer", "Editor") and
     * a list of names of the individuals who performed that role for the movie.
     * Useful for grouping non-primary crew roles in {@link CrewInfoDTO}.
     * </p>
     */
    public static class CrewCreditDTO {
        protected String role;
        protected List<String> name;

        public CrewCreditDTO(String role, List<String> name) {
            this.role = role;
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }
    }

    // Getters & Setters
    public List<String> getDirectors() {
        return directors;
    }

    public List<String> getScreenwriters() {
        return screenwriters;
    }

    public List<String> getProducers() {
        return producers;
    }

    public List<CrewCreditDTO> getOtherCredits() {
        return otherCredits;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public void setScreenwriters(List<String> screenwriters) {
        this.screenwriters = screenwriters;
    }

    public void setOtherCredits(List<CrewCreditDTO> otherCredits) {
        this.otherCredits = otherCredits;
    }
}


