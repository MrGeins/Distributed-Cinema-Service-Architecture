package it.unito.iumtweb.springboot;

import it.unito.iumtweb.springboot.actors.Actors;
import it.unito.iumtweb.springboot.actors.ActorsRepository;
import it.unito.iumtweb.springboot.countries.Countries;
import it.unito.iumtweb.springboot.countries.CountriesRepository;
import it.unito.iumtweb.springboot.crew.Crew;
import it.unito.iumtweb.springboot.crew.CrewRepository;
import it.unito.iumtweb.springboot.genres.Genres;
import it.unito.iumtweb.springboot.genres.GenresRepository;
import it.unito.iumtweb.springboot.languages.Languages;
import it.unito.iumtweb.springboot.languages.LanguagesRepository;
import it.unito.iumtweb.springboot.movies.Movies;
import it.unito.iumtweb.springboot.movies.MoviesRepository;
import it.unito.iumtweb.springboot.posters.Posters;
import it.unito.iumtweb.springboot.posters.PostersRepository;
import it.unito.iumtweb.springboot.releases.Releases;
import it.unito.iumtweb.springboot.releases.ReleasesRepository;
import it.unito.iumtweb.springboot.studios.Studios;
import it.unito.iumtweb.springboot.studios.StudiosRepository;
import it.unito.iumtweb.springboot.themes.Themes;
import it.unito.iumtweb.springboot.themes.ThemesRepository;
import it.unito.iumtweb.springboot.theoscarawards.TheOscarAwards;
import it.unito.iumtweb.springboot.theoscarawards.TheOscarAwardsRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional // rollback automatico al termine del test
class SpringbootApplicationTests {

    @Autowired
    private MoviesRepository moviesRepository;
    @Autowired
    private ActorsRepository actorsRepository;
    @Autowired
    private CrewRepository crewsRepository;
    @Autowired
    private CountriesRepository countriesRepository;
    @Autowired
    private GenresRepository genresRepository;
    @Autowired
    private LanguagesRepository languagesRepository;
    @Autowired
    private PostersRepository postersRepository;
    @Autowired
    private ReleasesRepository releasesRepository;
    @Autowired
    private StudiosRepository studiosRepository;
    @Autowired
    private ThemesRepository themesRepository;
    @Autowired
    private TheOscarAwardsRepository TheOscarAwardsRepository;

    @Test
    void contextLoads() {
        System.out.println("Context loads successfully.");
    }

    @BeforeEach
    void populateTest() {
        // --- Movies ---
        List<Movies> movies = List.of(
                new Movies("1000001", "Barbie", "Barbie and Ken are having the time of their lives in Barbie Land...", "2023", "She's everything. He's just Ken.", 114, "3.86"),
                new Movies("1000002", "Parasite", "All unemployed, Ki-taek's family takes peculiar interest...", "2019", "Act like you own the place.", 133, "4.56"),
                new Movies("1000003", "Everything Everywhere All at Once", "An aging Chinese immigrant is swept up in an insane adventure...", "2022", "The universe is so much bigger than you realize.", 140, "4.3"),
                new Movies("1000004", "Fight Club", "A ticking-time-bomb insomniac and a slippery soap salesman channel...", "1999", "Mischief. Mayhem. Soap.", 139, "4.27"),
                new Movies("1000005", "La La Land", "Mia, an aspiring actress, serves lattes to movie stars...", "2016", "Here's to the fools who dream.", 129, "4.09"),
                new Movies("1000006", "Oppenheimer", "The story of J. Robert Oppenheimer's role in the development...", "2023", "The world forever changes.", 181, "4.23"),
                new Movies("1000007", "Interstellar", "The adventures of a group of explorers who make use of a newly discovered wormhole...", "2014", "Mankind was born on Earth. It was never meant to die here.", 169, "4.35"),
                new Movies("1000008", "Joker", "During the 1980s, a failed stand-up comedian is driven insane...", "2019", "Put on a happy face.", 122, "3.85"),
                new Movies("1000009", "Dune", "Paul Atreides, a brilliant and gifted young man born into a great destiny...", "2021", "Beyond fear, destiny awaits.", 155, "3.9"),
                new Movies("1000010", "Pulp Fiction", "A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll...", "1994", "Just because you are a character doesn't mean you have character.", 154, "4.26"),
                new Movies("1000011", "Spider-Man: Into the Spider-Verse", "Struggling to find his place in the world while juggling school and family...", "2018", "More than one wears the mask.", 117, "4.42"),
                new Movies("1000012", "Whiplash", "Under the direction of a ruthless instructor, a talented young drummer...", "2014", "The road to greatness can take you to the edge.", 107, "4.43"),
                new Movies("1000013", "The Batman", "In his second year of fighting crime, Batman uncovers corruption in Gotham City...", "2022", "Unmask the truth.", 177, "3.98"),
                new Movies("1000014", "Get Out", "Chris and his girlfriend Rose go upstate to visit her parents for the weekend...", "2017", "Just because you're invited, doesn't mean you're welcome.", 104, "4.16"),
                new Movies("1000015", "Knives Out", "When renowned crime novelist Harlan Thrombey is found dead...", "2019", "Hell, any of them could have done it.", 131, "3.99"),
                new Movies("1000016", "Midsommar", "Several friends travel to Sweden to study as anthropologists...", "2019", "Let the festivities begin.", 147, "3.78"),
                new Movies("1000017", "The Dark Knight", "Batman raises the stakes in his war on crime...", "2008", "Why So Serious?", 152, "4.47"),
                new Movies("1000018", "Inception", "Cobb, a skilled thief who commits corporate espionage...", "2010", "Your mind is the scene of the crime.", 148, "4.2"),
                new Movies("1000019", "Saltburn", "Struggling to find his place at Oxford University...", "2023", "We're all about to lose our minds.", 131, "3.43"),
                new Movies("1000020", "Poor Things", "Brought back to life by an unorthodox scientist...", "2023", "She's like nothing you've ever seen.", 142, "4.05")
        );
        moviesRepository.saveAll(movies);

        // --- Actors ---
        List<Actors> actors = List.of(
                new Actors("1000001", "Margot Robbie", "Barbie"),
                new Actors("1000001", "Ryan Gosling", "Ken"),
                new Actors("1000001", "America Ferrera", "Gloria"),
                new Actors("1000001", "Ariana Greenblatt", "Sasha"),
                new Actors("1000001", "Issa Rae", "Barbie"),
                new Actors("1000002", "Song Kang-ho", "Kim Ki-taek"),
                new Actors("1000002", "Lee Sun-kyun", "Park Dong-ik"),
                new Actors("1000002", "Cho Yeo-jeong", "Yeon-kyo"),
                new Actors("1000002", "Choi Woo-shik", "Ki-woo"),
                new Actors("1000002", "Park So-dam", "Ki-jung"),
                new Actors("1000003", "Michelle Yeoh", "Evelyn Wang"),
                new Actors("1000003", "Ke Huy Quan", "Waymond Wang"),
                new Actors("1000003", "Stephanie Hsu", "Joy Wang / Jobu Tupaki"),
                new Actors("1000003", "James Hong", "Gong Gong"),
                new Actors("1000003", "Jamie Lee Curtis", "Deirdre Beaubeirdre"),
                new Actors("1000004", "Edward Norton", "Narrator"),
                new Actors("1000004", "Brad Pitt", "Tyler Durden"),
                new Actors("1000004", "Helena Bonham Carter", "Marla Singer"),
                new Actors("1000004", "Meat Loaf", "Robert Paulson"),
                new Actors("1000004", "Jared Leto", "Angel Face"),
                new Actors("1000005", "Ryan Gosling", "Sebastian"),
                new Actors("1000005", "Emma Stone", "Mia"),
                new Actors("1000005", "John Legend", "Keith"),
                new Actors("1000005", "Rosemarie DeWitt", "Laura"),
                new Actors("1000005", "J.K. Simmons", "Bill"),
                new Actors("1000006", "Cillian Murphy", "J. Robert Oppenheimer"),
                new Actors("1000006", "Emily Blunt", "Kitty Oppenheimer"),
                new Actors("1000006", "Matt Damon", "Leslie Groves"),
                new Actors("1000006", "Robert Downey Jr.", "Lewis Strauss"),
                new Actors("1000006", "Florence Pugh", "Jean Tatlock")
        );
        actorsRepository.saveAll(actors);

        // --- Countries ---
        List<Countries> countries = List.of(
                new Countries()
        );
        countriesRepository.saveAll(countries);

        // --- Crew ---
        List<Crew> crews = List.of(
                new Crew("1000001","Director","Greta Gerwig"),
                new Crew("1000001","Producer","Tom Ackerley"),
                new Crew("1000001","Writer","Greta Gerwig"),
                new Crew("1000001","Casting","Lucy Bevan"),
                new Crew("1000002","Director","Bong Joon-ho"),
                new Crew("1000002","Producer","Bong Joon-ho"),
                new Crew("1000002","Writer","Kim Dae-hwan"),
                new Crew("1000002","Casting","Shin Jae-hong"),
                new Crew("1000003","Director","Daniel Scheinert"),
                new Crew("1000003","Producer","Joe Russo"),
                new Crew("1000003","Writer","Daniel Kwan"),
                new Crew("1000003","Casting","Avy Kaufman"),
                new Crew("1000004","Director","David Fincher"),
                new Crew("1000004","Producer","Art Linson"),
                new Crew("1000004","Writer","Jim Uhls"),
                new Crew("1000004","Casting","Judy Henderson"),
                new Crew("1000005","Director","Damien Chazelle"),
                new Crew("1000005","Producer","Fred Berger"),
                new Crew("1000005","Writer","Damien Chazelle"),
                new Crew("1000005","Casting","Ellen Chenoweth"),
                new Crew("1000006","Director","Christopher Nolan"),
                new Crew("1000006","Producer","Emma Thomas"),
                new Crew("1000006","Writer","Christopher Nolan"),
                new Crew("1000006","Casting","Carmen Cuba")
        );
        crewsRepository.saveAll(crews);

        // --- Genres ---
        List<Genres> genres = List.of(
                new Genres("1000001", "Comedy" ),
                new Genres("1000001", "Fantasy" ),
                new Genres("1000002", "Thriller" ),
                new Genres("1000002", "Drama" ),
                new Genres("1000003", "Sci-Fi" ),
                new Genres("1000003", "Adventure" ),
                new Genres("1000004", "Drama" ),
                new Genres("1000004", "Thriller" ),
                new Genres("1000005", "Musical" ),
                new Genres("1000005", "Romance" ),
                new Genres("1000006", "Biography" ),
                new Genres("1000006", "Drama"),
                new Genres("1000007", "Sci-Fi"),
                new Genres("1000007", "Adventure"),
                new Genres("1000008", "Crime"),
                new Genres("1000008", "Drama"),
                new Genres("1000009", "Sci-Fi"),
                new Genres("1000009", "Adventure"),
                new Genres("1000010", "Crime"),
                new Genres("1000010", "Drama"),
                new Genres("1000011", "Animation"),
                new Genres("1000011", "Action"),
                new Genres("1000012", "Drama"),
                new Genres("1000012", "Music"),
                new Genres("1000013", "Action"),
                new Genres("1000013", "Crime"),
                new Genres("1000014", "Horror"),
                new Genres("1000014", "Thriller"),
                new Genres("1000015", "Mystery"),
                new Genres("1000015", "Comedy"),
                new Genres("1000016", "Horror"),
                new Genres("1000016", "Drama"),
                new Genres("1000017", "Action"),
                new Genres("1000017", "Crime"),
                new Genres("1000018", "Sci-Fi"),
                new Genres("1000018", "Action"),
                new Genres("1000019", "Drama"),
                new Genres("1000019", "Thriller"),
                new Genres("1000020", "Drama"),
                new Genres("1000020", "Fantasy")
        );
        genresRepository.saveAll(genres);

        // --- Languages ---
        List<Languages> languages = List.of(
                new  Languages("1000001", "English"),
                new  Languages("1000002", "Korean"),
                new  Languages("1000003", "English"),
                new  Languages("1000004", "English"),
                new  Languages("1000005", "English"),
                new  Languages("1000006", "English"),
                new  Languages("1000007", "English"),
                new  Languages("1000008", "English"),
                new  Languages("1000009", "English"),
                new  Languages("1000010", "English"),
                new  Languages("1000011", "English"),
                new  Languages("1000012", "English"),
                new  Languages("1000013", "English"),
                new  Languages("1000014", "English"),
                new  Languages("1000015", "English"),
                new  Languages("1000016", "English"),
                new  Languages("1000017", "English"),
                new  Languages("1000018", "English"),
                new  Languages("1000019", "English"),
                new  Languages("1000020", "English")
        );
        languagesRepository.saveAll(languages);

        // --- Posters ---
        List<Posters> posters = List.of(
                new Posters("1000001", "https://image.tmdb.org/t/p/w500/7BqBCz3JkV0Vw5h8PltLqLJp7PR.jpg"),
                new Posters("1000002", "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"),
                new Posters("1000003", "https://image.tmdb.org/t/p/w500/uMZ3k6Uz9u8b1xXo5teLoW5k6Z.jpg"),
                new Posters("1000004", "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"),
                new Posters("1000005", "https://image.tmdb.org/t/p/w500/qZkvm4qX1Tz9b8sQW7DLGm5iW5.jpg"),
                new Posters("1000006", "https://image.tmdb.org/t/p/w500/5pe2wXg8bLqOYfI5QY7B6j7d8j.jpg"),
                new Posters("1000007", "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg"),
                new Posters("1000008", "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg"),
                new Posters("1000009", "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"),
                new Posters("1000010", "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg"),
                new Posters("1000011", "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Ux.jpg"),
                new Posters("1000012", "https://image.tmdb.org/t/p/w500/oPxnXfzYkOOlBQCk52Uh1veL3A.jpg"),
                new Posters("1000013", "https://image.tmdb.org/t/p/w500/74xTEgt7R36Fpooo50r9T25onh.jpg"),
                new Posters("1000014", "https://image.tmdb.org/t/p/w500/2XDFkM7Eh2gqN3wKZ8Yd8lWb5l.jpg"),
                new Posters("1000015", "https://image.tmdb.org/t/p/w500/mvYpKlpFukTivnlBhizGbkAe3v.jpg"),
                new Posters("1000016", "https://image.tmdb.org/t/p/w500/7LEI8Y5rX6gAtxYkC2b52j4sS6.jpg"),
                new Posters("1000017", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"),
                new Posters("1000018", "https://image.tmdb.org/t/p/w500/edv5CZvWj09upOsy2Y6IwDhK8bt.jpg"),
                new Posters("1000019", "https://image.tmdb.org/t/p/w500/8ZcmTlzxF7bP1O6yYki3WTruC5.jpg"),
                new Posters("1000020", "https://image.tmdb.org/t/p/w500/5bFK5d3mVTAvBCXi5NPWH0tYjKl.jpg")
        );
        postersRepository.saveAll(posters);

        // --- Releases ---
        List<Releases> releases = List.of(
                new Releases("1000001", "2023-07-21", "United States", "Theatrical", "PG-13"),
                new Releases("1000002", "2019-05-30", "South Korea", "Theatrical", "R"),
                new Releases("1000003", "2022-03-11", "United States", "Theatrical", "R"),
                new Releases("1000004", "1999-10-15", "United States", "Theatrical", "R"),
                new Releases("1000005", "2016-12-09", "United States", "Theatrical", "PG-13"),
                new Releases("1000006", "2023-07-21", "United States", "Theatrical", "R"),
                new Releases("1000007", "2014-11-05", "United States", "Theatrical", "PG-13"),
                new Releases("1000008", "2019-10-04", "United States", "Theatrical", "R"),
                new Releases("1000009", "2021-10-22", "United States", "Theatrical", "PG-13"),
                new Releases("1000010", "1994-10-14", "United States", "Theatrical", "R"),
                new Releases("1000011", "2018-12-14", "United States", "Theatrical", "PG"),
                new Releases("1000012", "2014-10-10", "United States", "Theatrical", "R"),
                new Releases("1000013", "2022-03-04", "United States", "Theatrical", "PG-13"),
                new Releases("1000014", "2017-02-24", "United States", "Theatrical", "R"),
                new Releases("1000015", "2019-11-27", "United States", "Theatrical", "PG-13"),
                new Releases("1000016", "2019-06-14", "United States", "Theatrical", "R"),
                new Releases("1000017", "2008-07-18", "United States", "Theatrical", "PG-13"),
                new Releases("1000018", "2010-07-16", "United States", "Theatrical", "PG-13"),
                new Releases("1000019", "2023-07-26", "United States", "Theatrical", "R"),
                new Releases("1000020", "2023-09-08", "United States", "Theatrical", "R")
        );
        releasesRepository.saveAll(releases);

        // --- Studios ---
        List<Studios> studios = List.of(
                new Studios("1000001", "Warner Bros."),
                new Studios("1000002", "CJ Entertainment"),
                new Studios("1000003", "A24"),
                new Studios("1000004", "20th Century Fox"),
                new Studios("1000005", "Lionsgate"),
                new Studios("1000006", "Universal Pictures"),
                new Studios("1000007", "Paramount Pictures"),
                new Studios("1000008", "Warner Bros."),
                new Studios("1000009", "Warner Bros."),
                new Studios("1000010", "Miramax"),
                new Studios("1000011", "Sony Pictures Animation"),
                new Studios("1000012", "Sony Pictures Classics"),
                new Studios("1000013", "Warner Bros."),
                new Studios("1000014", "Universal Pictures"),
                new Studios("1000015", "Lionsgate"),
                new Studios("1000016", "A24"),
                new Studios("1000017", "Warner Bros."),
                new Studios("1000018", "Warner Bros."),
                new Studios("1000019", "Amazon Studios"),
                new Studios("1000020", "Searchlight Pictures")
        );
        studiosRepository.saveAll(studios);

        // --- Themes ---
        List<Themes> themes = List.of(
                new Themes("1000001", "Fantasy"),
                new Themes("1000002", "Social Commentary"),
                new Themes("1000003", "Multiverse"),
                new Themes("1000004", "Anarchy"),
                new Themes("1000005", "Dreams"),
                new Themes("1000006", "Science"),
                new Themes("1000007", "Space Exploration"),
                new Themes("1000008", "Mental Illness"),
                new Themes("1000009", "Politics"),
                new Themes("1000010", "Crime"),
                new Themes("1000011", "Heroism"),
                new Themes("1000012", "Ambition"),
                new Themes("1000013", "Justice"),
                new Themes("1000014", "Racism"),
                new Themes("1000015", "Mystery"),
                new Themes("1000016", "Cult"),
                new Themes("1000017", "Chaos"),
                new Themes("1000018", "Dreams"),
                new Themes("1000019", "Obsession"),
                new Themes("1000020", "Identity")
        );
        themesRepository.saveAll(themes);

        // --- TheOscarAwards ---
        List<TheOscarAwards> theOscarAwards = List.of(
                new TheOscarAwards(2023, 2022, 1, "ACTOR", "Brendan Fraser", "The Whale", true),
                new TheOscarAwards(2023, 2022, 1, "ACTRESS", "Michelle Yeoh", "Everything Everywhere All at Once", true),
                new TheOscarAwards(2023, 2022, 1, "DIRECTOR", "Daniel Kwan and Daniel Scheinert", "Everything Everywhere All at Once", true),
                new TheOscarAwards(2023, 2022, 1, "BEST PICTURE", null, "Everything Everywhere All at Once", true),
                new TheOscarAwards(2020, 2019, 92, "ACTOR", "Joaquin Phoenix", "Joker", true),
                new TheOscarAwards(2020, 2019, 92, "ACTRESS", "Renée Zellweger", "Judy", true),
                new TheOscarAwards(2020, 2019, 92, "DIRECTOR", "Bong Joon-ho", "Parasite", true),
                new TheOscarAwards(2020, 2019, 92, "BEST PICTURE", null, "Parasite", true)
        );
        TheOscarAwardsRepository.saveAll(theOscarAwards);
    }

    @Test
    void addMoviesAndActorsToDatabase() {
        List<Movies> movies = moviesRepository.findAll();
        List<Actors> actors = actorsRepository.findAll();

        assertNotNull(movies);
        assertNotNull(actors);
        System.out.println("Movies and actors added successfully.");
    }

    @Test
    void testDatabasePopulation() {
        assertEquals(20, moviesRepository.count());
        assertTrue(actorsRepository.count() > 0);
        assertTrue(moviesRepository.findByMovieId("1000001").isPresent());
        assertFalse(actorsRepository.findByNameIgnoreCase("Margot Robbie").isEmpty());

        System.out.println("Database populated successfully with all test data.");
    }

}
