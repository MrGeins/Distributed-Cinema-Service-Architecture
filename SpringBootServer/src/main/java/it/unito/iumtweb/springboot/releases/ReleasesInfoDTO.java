package it.unito.iumtweb.springboot.releases;

/**
 * Data Transfer Object (DTO) representing a movie release instance, with support for pagination.
 * <p>
 * This class encapsulates details about a specific movie release, such as date, country, and
 * the associated movie ID, as well as common pagination parameters (page and size) for use in API requests.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * ReleaseInfoDTO releaseRequest = new ReleaseInfoDTO("2022-12-15", "US", "MOV123", 1, 10);
 * }
 * </pre>
 * </p>
 */
public class ReleasesInfoDTO {
    private String movieId;
    private String country;
    private String date;

    /**
     * Constructs a {@code ReleaseInfoDTO} with the specified details of releases.
     *
     * @param movieId the associated movie's identifier
     * @param country the country of release
     * @param date    the release date
     */
    public ReleasesInfoDTO(String movieId, String country, String date) {
        this.movieId = movieId;
        this.country = country;
        this.date = date;
    }

    // Getter & Setter
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
