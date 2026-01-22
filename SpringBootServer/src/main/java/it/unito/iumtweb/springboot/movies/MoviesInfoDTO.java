package it.unito.iumtweb.springboot.movies;

import it.unito.iumtweb.springboot.actors.Actors;
import it.unito.iumtweb.springboot.countries.Countries;
import it.unito.iumtweb.springboot.crew.CrewInfoDTO;
import it.unito.iumtweb.springboot.genres.Genres;
import it.unito.iumtweb.springboot.languages.Languages;
import it.unito.iumtweb.springboot.posters.Posters;
import it.unito.iumtweb.springboot.releases.Releases;
import it.unito.iumtweb.springboot.studios.Studios;
import it.unito.iumtweb.springboot.themes.Themes;

import java.util.List;

/**
* Data Transfer Object (DTO) providing comprehensive information about a movie.
* <p>
* This class aggregates all relevant details for a movie, including core movie data,
* poster image, cast, crew, country, languages, releases, genres, studios, themes, and a list of related movies.
* It is typically used for delivering a complete movie info view in APIs or UI backends.
* </p>
*
* <p>
* Example usage:
* <pre>
* {@code
* MoviesInfoDTO info = new MoviesInfoDTO(movie, actors, crew, countries, genres, languages, posters, releases, studios, themes, related);
* }
* </pre>
* </p>
*/
public class MoviesInfoDTO {
    private Movies data;
    private List<Actors> actors;
    private CrewInfoDTO crew;
    private List<Countries> countries;
    private List<Genres> genres;
    private List<Languages> languages;
    private List<Posters> posters;
    private List<Releases> releases;
    private List<Studios> studios;
    private List<Themes> themes;
    private List<MoviesCardInfoDTO> relatedMovies;

    /**
    * Constructs a {@code MovieInfoDTO} aggregating all relevant movie data and related entities.
    *
    * @param data          primary movie data entity
    * @param actors        list of actors
    * @param crew          crew information object
    * @param countries     list of countries associated with the movie
    * @param genres        genres classifying the movie
    * @param languages     list of languages available for the movie
    * @param studios       studios associated with the movie
    * @param releases      release details/versions
    * @param posters       list of URL/path for the poster image
    * @param themes        thematic elements
    * @param relatedMovies list of related movies
    */
    public MoviesInfoDTO(Movies data, List<Actors> actors, CrewInfoDTO crew, List<Countries> countries, List<Genres> genres, List<Languages> languages, List<Posters> posters, List<Releases> releases, List<Studios> studios, List<Themes> themes, List<MoviesCardInfoDTO> relatedMovies) {
        this.data = data;
        this.actors = actors;
        this.crew = crew;
        this.countries = countries;
        this.genres = genres;
        this.languages = languages;
        this.posters = posters;
        this.releases = releases;
        this.studios = studios;
        this.themes = themes;
        this.relatedMovies = relatedMovies;
    }

    // Getter & Setter
    public Movies getData() {
        return data;
    }

    public void setData(Movies data) {
        this.data = data;
    }

    public List<Actors> getActors() {
        return actors;
    }

    public void setActors(List<Actors> actors) {
        this.actors = actors;
    }

    public CrewInfoDTO getCrew() {
        return crew;
    }

    public void setCrew(CrewInfoDTO crew) {
        this.crew = crew;
    }

    public List<Countries> getCountries() {
        return countries;
    }

    public void setCountries(List<Countries> countries) {
        this.countries = countries;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public List<Languages> getLanguages() {
    return languages;
    }

    public void setLanguages(List<Languages> languages) {
        this.languages = languages;
    }

    public List<Posters> getPosters() {
        return posters;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
    }

    public List<Releases> getReleases() {
        return releases;
    }

    public void setReleases(List<Releases> releases) {
        this.releases = releases;
    }

    public List<Studios> getStudios() {
        return studios;
    }

    public void setStudios(List<Studios> studios) {
        this.studios = studios;
    }

    public List<Themes> getThemes() {
        return themes;
    }

    public void setThemes(List<Themes> themes) {
        this.themes = themes;
    }

    public List<MoviesCardInfoDTO> getRelatedMovies() {
        return relatedMovies;
    }

    public void setRelatedMovies(List<MoviesCardInfoDTO> relatedMovies) {
        this.relatedMovies = relatedMovies;
    }

}
