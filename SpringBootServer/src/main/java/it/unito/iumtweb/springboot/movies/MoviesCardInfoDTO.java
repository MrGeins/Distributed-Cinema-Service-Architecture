package it.unito.iumtweb.springboot.movies;

/**
 * Data Transfer Object (DTO) interface that represents summary information about a movie.
 * <p>
 * This interface is used as a Projection to encapsulate key details needed to display
 * a movie card in listings, such as identifiers, title, tagline, description, rating,
 * year of release, link to the movie's poster, roles played, and Oscar wins.
 * </p>
 *
 * <p>
 * It facilitates efficient transfer of essential movie data from the PostgreSQL database
 * to the Central Server. Being a Projection, Spring Data JPA automatically maps the
 * result of native SQL queries to this interface.
 * </p>
 */
public interface MoviesCardInfoDTO {
    Long getId();
    String getMovieId();
    String getMovieTitle();
    String getTagline();
    String getDescription();
    String getRating();
    String getYearOfRelease();
    String getPosterLink();
    String getRoles();
    Boolean getOscarWinner();
}


/*
 * Data Transfer Object (DTO) that represents summary information about a movie.
 * <p>
 * This class encapsulates key details needed to display a movie card in listings,
 * such as identifiers, title, tagline, description, rating, year of release, link
 * to the movie's poster, roles played, oscar wins. It facilitates efficient transfer of essential movie data
 * between layers or over network requests in applications like REST APIs or UI components.
 * </p>
 *
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * MoviesCardInfoDTO card = new MoviesCardInfoDTO(
 *     "MOV456",
 *     "Another Adventure",
 *     "A tale worth telling.",
 *     "A story full of wonder and excitement.",
 *     7.8,
 *     "2021",
 *     "http://example.com/poster2.jpg"
 *     "Lead Actor",
 *     true
 * );
 * }
 * </pre>
 * </p>
 */
/*public class MoviesCardInfoDTO {
    private Long id;
    private String movieId;
    private String movieTitle;
    private String tagline;
    private String description;
    private String rating;
    private String yearOfRelease;
    private String posterLink;
    private String roles;
    private Boolean oscarWinner;*/

    /*
     * Constructs a {@code MovieCardInfoDTO} with the specified details.
     *
     * @param movieId       the business or external identifier for the movie
     * @param movieTitle    the official movie title
     * @param tagline       the short tagline for the movie
     * @param description   a brief description or synopsis
     * @param rating        rating of the movie
     * @param yearOfRelease year the movie was released
     * @param posterLink    a URL or path to the movie's poster image
     * @param roles         actor roles in the movie
     * @param oscarWinner   true if the movie has won an Oscar, false otherwise
     */
    /*public MoviesCardInfoDTO(String movieId, String movieTitle, String tagline, String description,
                            String rating, String yearOfRelease, String posterLink, String roles, Boolean oscarWinner) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.tagline = tagline;
        this.description = description;
        System.out.println(rating == null);
        this.rating = rating;
        this.yearOfRelease = yearOfRelease;
        this.posterLink = posterLink;
        this.roles = roles;
        this.oscarWinner = oscarWinner;
    }


    public MoviesCardInfoDTO(String movieId, String movieTitle, String tagline, String description,
                             Double rating, String yearOfRelease, String posterLink) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.tagline = tagline;
        this.description = description;
        this.rating = rating;
        this.yearOfRelease = yearOfRelease;
        this.posterLink = posterLink;
        this.roles = "N/A"; // Valore di default
        this.oscarWinner = false; // Valore di default
    }

    // Getter & Setter


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Boolean isOscarWinner() {
        return oscarWinner;
    }

    public void setOscarWinner(Boolean oscarWinner) {
        this.oscarWinner = oscarWinner;
    }

    @Override
    public String toString() {
        return "MoviesCardInfoDTO{" +
                "id=" + id +
                ", movieId='" + movieId + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", tagline='" + tagline + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", yearOfRelease='" + yearOfRelease + '\'' +
                ", posterLink='" + posterLink + '\'' +
                ", roles='" + roles + '\'' +
                ", oscarWinner=" + oscarWinner +
                '}';
    }
}*/
