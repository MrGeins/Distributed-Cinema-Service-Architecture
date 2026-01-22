package it.unito.iumtweb.springboot.crew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class that provides high-level operations for retrieving and analyzing
 * Crew data. This class acts as an intermediate layer between the controller
 * and the repository, converting entities into DTOs when needed.
 */
@Service
public class CrewService {
    private final CrewRepository repo;

    /**
     * Constructs a new CrewService with the specified CrewRepository.
     *
     * @param repo the repository for crew entities
     */
    @Autowired
    public CrewService(CrewRepository repo) {
        this.repo = repo;
    }

    /**
     * Retrieves all crew members.
     *
     * @return a list of all {@link Crew} entities
     */
    public List<Crew> getAllCrewMember() {
        return repo.findAll();
    }

    /**
     * Retrieves and organizes crew information for a specific movie.
     *
     * <p>The method performs the following operations:</p>
     * <ul>
     *     <li>Fetches director, producer, and screenwriter names through dedicated repository queries.</li>
     *     <li>Retrieves the full list of crew members associated with the movie.</li>
     *     <li>If no crew is found, returns an empty {@link CrewInfoDTO}.</li>
     *     <li>Groups all remaining crew members by role, excluding the main roles
     *         (Director, Producer, Writer), and maps them into {@link CrewInfoDTO.CrewCreditDTO} objects.</li>
     * </ul>
     *
     * @param movieId the unique identifier of the movie whose crew information is requested
     * @return a {@link CrewInfoDTO} containing:
     *         <ul>
     *             <li>a list of directors</li>
     *             <li>a list of producers</li>
     *             <li>a list of screenwriters</li>
     *             <li>a list of other crew roles with their associated member names</li>
     *         </ul>
     */
    public CrewInfoDTO getCrewByMovieId(String movieId) {
        List<String> directors = repo.findDirectorNamesByMovieId(movieId);
        List<String> producers = repo.findProducerNamesByMovieId(movieId);
        List<String> screenwriters = repo.findScreenwriterNamesByMovieId(movieId);
        List<Crew> allCrew = repo.findCrewByMovieId(movieId);
        if (allCrew.isEmpty()) {
            return new CrewInfoDTO(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }
        Map<String, List<String>> crewByRole = allCrew.stream()
                .collect(Collectors.groupingBy(
                        Crew::getRole,
                        Collectors.mapping(Crew::getName, Collectors.toList())
                ));
        List<CrewInfoDTO.CrewCreditDTO> otherCredits = crewByRole.entrySet().stream()
                .filter(entry -> !List.of("Director", "Producer", "Writer").contains(entry.getKey()))
                .map(entry -> new CrewInfoDTO.CrewCreditDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new CrewInfoDTO(directors, producers, screenwriters, otherCredits);
    }
}
