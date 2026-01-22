package it.unito.iumtweb.springboot.theoscarawards;

import jakarta.persistence.*;

/**
 * Entity class representing an Oscar Award record.
 */
@Entity
@Table(name = "TheOscarAwards")
public class TheOscarAwards {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer yearFilm;
    private Integer yearCeremony;
    private Integer ceremony;
    @Column(columnDefinition = "TEXT")
    private String category;
    @Column(columnDefinition = "TEXT")
    private String actorName;
    @Column(columnDefinition = "TEXT")
    private String filmTitle;
    @Column(columnDefinition = "BOOLEAN")
    private Boolean winner;

    /**
     * Default constructor for TheOscarAwards.
     */
    public TheOscarAwards() {}

    /**
     * Constructor for TheOscarAwards with the specified details.
     *
     * @param yearFilm     the year of the film
     * @param yearCeremony the year of the ceremony
     * @param ceremony     the ceremony number
     * @param category     the award category
     * @param actorName    the name of the actor
     * @param filmTitle    the title of the film
     * @param winner       whether the award was won
     */
    public TheOscarAwards(Integer yearFilm, Integer yearCeremony, Integer ceremony, String category, String actorName, String filmTitle, Boolean winner) {
        this.yearFilm = yearFilm;
        this.yearCeremony = yearCeremony;
        this.ceremony = ceremony;
        this.category = category;
        this.actorName = actorName;
        this.filmTitle = filmTitle;
        this.winner = winner;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYearFilm() {
        return yearFilm;
    }

    public void setYearFilm(Integer yearFilm) {
        this.yearFilm = yearFilm;
    }

    public Integer getYearCeremony() {
        return yearCeremony;
    }

    public void setYearCeremony(Integer yearCeremony) {
        this.yearCeremony = yearCeremony;
    }

    public Integer getCeremony() {
        return ceremony;
    }

    public void setCeremony(Integer ceremony) {
        this.ceremony = ceremony;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String name) {
        this.actorName = name;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String film) {
        this.filmTitle = film;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }
}

