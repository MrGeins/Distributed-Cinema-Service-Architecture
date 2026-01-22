package it.unito.iumtweb.springboot.releases;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for endpoints related to movie releases (release dates and classifications).
 * Allows retrieval of release information for specific movies, with support for pagination.
 * All endpoints are mapped under <code>/releases</code>.
 */
@RestController
@RequestMapping("/releases")
public class ReleasesController {
    private final ReleasesService releasesService;

    public ReleasesController(ReleasesService releasesService) {
        this.releasesService = releasesService;
    }

    /**
     * Retrieves a paginated list of movies belonging to a specific release.
     *
     * @param moviesId the unique identifier of the movie
     * @param page  the page number to retrieve (zero-based)
     * @param size  the number of results per page
     * @return a list of {@link ReleasesInfoDTO} object for movies in the specified release
     */
    @GetMapping("/movies/{moviesId}")
    public ResponseEntity<Page<ReleasesInfoDTO>> getReleases(@PathVariable String moviesId,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "12") Integer size) {
        if (moviesId == null || moviesId.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            Page<ReleasesInfoDTO> releases = releasesService.getReleasesByMovieId(moviesId,page,size);
            return ResponseEntity.ok(releases);
        }
    }
}
