let express = require('express');
const axios = require('axios');
const cors = require('cors');
let router = express.Router();
router.use(cors())
const {getHomePage, searchMovies, getHomePageCarousel} = require("../controllers/indexController");
const {validateSearchParams} = require("../middlewares/validation");


// ricerca
/*router.get('/search', async function (req, res, next) {
    const { title, minScore, maxScore } = req.query;
    //let minScore = req.body.minScore;
    //let maxScore = req.body.maxScore;

    if (!title) {
        return res.render('partials/reviews.hbs', {
            title: "Risultati Ricerca",
            reviews_found: "" // Vuoto all'inizio
        });
    }

    const min = parseFloat(minScore);
    const max = parseFloat(maxScore);

    if (isNaN(min) || isNaN(max) || min > max) {
        return res.status(400).json({ error: "Invalid range!" });
    }

    try {
        // 1. Chiamata al Server Backend (Mongo) sulla porta 3001
        // Passiamo tutti i parametri ricevuti dal form
        const response = await axios.get("http://localhost:3001/reviews/search", {
            params: {
                title: title,
                minScore: minScore,
                maxScore: maxScore
                // Aggiungiamo anche gli altri campi se il backend li supporta
                //topCritic: req.body.topCritic,
                //reviewType: req.body.reviewType,
                //startDate: req.body.startDate,
                //endDate: req.body.endDate
            }
        });

        const reviews = response.data; // Assumiamo che il backend restituisca un array di oggetti

        // 2. Formattazione della risposta per il Frontend
        // Il tuo frontend (review-search.js) si aspetta una stringa HTML dentro "reviews_found"
        // per iniettarla direttamente nel DOM.

        if (!reviews || reviews.length === 0) {
            return res.json({
                reviews_found: "No records found for movie title: '" + title + "'"
            });
        }

        // Costruiamo le CARD per la griglia
        let htmlOutput = "";
        reviews.forEach(review => {
            const safeTitle = (review.movieName || 'Film').replace(/'/g, "\\'");
            const id = review._id || safeTitle;

            // Generiamo una CARD invece di un div semplice
            htmlOutput += `
                <div class="movie-card">
                    <div class="movie-info">
                        <div class="movie-title">${review.movieName || 'Titolo Sconosciuto'}</div>
                        <div class="movie-meta">
                            <span> ${review.score || 'N/A'}</span>
                            <span>${review.critic_name || 'Critico'}</span>
                        </div>
                        
                        <button onclick="openChat('${safeTitle}', '${id}')" class="chat-btn">

                        </button>
                    </div>
                </div>`;
        });

        res.json({
            title: title,
            reviews_found: htmlOutput
        });

    } catch (error) {
        console.error("Errore chiamando il backend:", error.message);
        res.status(500).json({
            error: "Errore di comunicazione con il database server."
        });
    }
}); */



//Url MongoDB
const BACKEND_API = 'http://localhost:3001';



router.get('/', function(req, res, next) {
    res.render('home', {
        title: "IUM-Flix Home",
        isHome: true,
        user: req.session.user
    });
});


router.get('/search-results', async function (req, res, next){
    let title = req.query.title;


    let minScore = req.query.minScore;
    let maxScore = req.query.maxScore;

    if (!title) {
        return res.render('partials/reviews.hbs', {
            title: "Risultati Ricerca",
            reviews_found: ""// Griglia vuota
        });
    }

    try {
        const response = await axios.get("http://localhost:3001/search", {
            params: {
                title: title,
                minScore: minScore,
                maxScore: maxScore,
                topCritic: req.query.topCritic,
                reviewType: req.query.reviewType,
                startDate: req.query.startDate,
                endDate: req.query.endDate
            }
        });

        const reviews = response.data.reviews;


        if (!reviews || reviews.length === 0) {
            return res.render('partials/reviews.hbs', {
                title: "Risultati per: " + title,
                searchQuery: title,
                reviews_found: `<p style="text-align:center; grid-column: 1/-1;">Nessun risultato trovato per '${title}'</p>`
            });
        }


        let htmlOutput = "";
        reviews.forEach(review => {
            const safeTitle = (review.movie_title || 'Film').replace(/'/g, "\\'");
            const id = review.id || safeTitle;

            htmlOutput += `
                <div class="movie-card">
                    <div class="movie-info">
                        <div class="movie-title">${review.movie_title || 'Titolo Sconosciuto'}</div>
                        <div class="movie-meta">
                            <span> ${review.score || 'N/A'}</span>
                            <span>${review.critic_name || 'Critico'}</span>
                        </div>
                        
                        <button onclick="openChat('${safeTitle}', '${id}')" class="chat-btn">
                        
                        </button>
                    </div>
                </div>`;
        });

        res.render('partials/reviews.hbs', {
            title: "Risultati per: " + title,
            searchQuery: title,
            reviews_found: htmlOutput
        });

    } catch (error) {
        console.error("Errore chiamando il backend:", error.message);
        res.render('partials/reviews.hbs', {
            title: "Errore Ricerca",
            reviews_found: `<p style="color:red; text-align:center; grid-column: 1/-1;">Errore di comunicazione con il database.</p>`
        });
    }
});


router.get('/searchForName', function (req, res) {
    const title = req.query.title;

    // Renderizziamo la pagina 'moviesFound'
    res.render('moviesFound', {
        title: title ? `Risultati per: ${title}` : "Cerca Film",
        searchQuery: title, // Questo finirà nell'input hidden
        isHome: false
    });
});


//get della pagina movies
router.get('/movies', function(req, res, next) {
    res.render('movies', {
        title: "Catalogo film"
    });
});

/*router.get('/api/movies', async function(req, res, next) {
    try {
        const { genre, year, page, size, sort } = req.query;

        // URL e parametri base
        let url = 'http://localhost:8080/movies/options';
        const params = { page, size, sort };

        // Flag per sapere se dobbiamo filtrare manualmente l'anno in Node.js
        // Lo attiviamo se l'utente ha scelto SIA un genere SIA un anno
        let needManualYearFilter = false;

        // --- LOGICA DI SCELTA ENDPOINT ---

        // CASO 1: C'è un GENERE (e forse un anno)
        if (genre && genre !== 'all') {
            url = `http://localhost:8080/movies/genre/${encodeURIComponent(genre)}`;

            // Se c'è anche l'anno, attiviamo il filtro manuale perché il backend lo ignorerebbe
            if (year && year !== 'all') {
                needManualYearFilter = true;
            }
        }
        // CASO 2: Solo ANNO (Genere è 'all')
        else if (year && year !== 'all') {
            url = `http://localhost:8080/movies/year/${encodeURIComponent(year)}`;
        }

        // --- CHIAMATA AL BACKEND ---
        const response = await axios.get(url, { params });
        let data = response.data;

        // --- FILTRAGGIO MANUALE (Il "Trucco") ---
        if (needManualYearFilter && data.content) {
            // Filtriamo l'array dei film ricevuto per tenere solo quelli dell'anno giusto.
            // Nota: movie.year deve corrispondere al nome della proprietà nel JSON del film.
            // Usiamo == per evitare problemi tra stringhe e numeri.
            data.content = data.content.filter(movie => movie.year == year);

            // Opzionale: Se il filtro svuota la pagina, potresti trovarti con meno di 20 film
            // o addirittura 0, ma è l'unico modo per avere i dati corretti senza toccare Java.
        }

        res.json(data);

    } catch (error) {
        console.error("Errore API Movies:", error.message);

        // Se il backend risponde 404 (nessun film trovato), restituiamo una lista vuota
        if (error.response && error.response.status === 404) {
            return res.json({ content: [], last: true });
        }

        res.status(500).json({ error: 'Error while loading movies!' });
    }
}); */



router.get('/api/movies', async function(req, res) {
    try {

        const { genre, year, page, size, sort, order } = req.query;


        const params = {
            page: page || 0,
            size: size || 20,
            sort: sort || 'rating',
            order: order || 'DSC'
        };


        if (genre && genre !== 'all') params.genre = genre;
        if (year && year !== 'all') params.year = year;

        const response = await axios.get('http://localhost:8080/movies/options', { params });


        res.json(response.data);

    } catch (error) {
        console.error("Errore Proxy Node.js -> Spring Boot:", error.message);


        if (error.response && error.response.status === 404) {
            return res.json({ content: [], last: true });
        }

        res.status(500).json({ error: 'Errore durante il recupero dei film dal database.' });
    }
});


router.get('/logout', function (req, res, next) {

    req.session.destroy((err) => {
        if (err) {
            console.error("Errore durante il logout:", err);
            return res.redirect('/');
        }


        res.clearCookie('connect.sid');


        res.redirect('/');
    });
});





router.get('/api/movies/searchForName', async function (req, res) {
    const title = req.query.title;
    const page = req.query.page || 0;
    const size = req.query.size || 20;

    try {
        // Chiamata al backend Spring Boot (8080)
        const response = await axios.get(`http://localhost:8080/movies/name/${encodeURIComponent(title)}`, {
            params: {
                page: page,
                size: size
            }
        });


        res.json(response.data);

    } catch (error) {

        if (error.response && error.response.status === 404) {
            return res.json({
                content: [],
                totalElements: 0,
                last: true
            });
        }

        console.error("Errore API Search:", error.message);
        res.status(500).json({ error: "Errore durante il recupero dei film" });
    }
});

router.get('/api/years', async function (req, res, next) {
    try {
        const response = await axios.get('http://localhost:8080/movies/years');
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: 'Error while loading years!' });
    }
})


/*router.get('/api/movies/year/:year', async function(req, res, next) {
    try {
        const year = req.params.year;

        // Importante: inoltriamo anche i parametri di paginazione (page, size, sort)
        // che arrivano dal frontend dentro req.query
        const response = await axios.get(`http://localhost:8080/movies/year/${year}`, {
            params: req.query
        });

        res.json(response.data);
    } catch (error) {
        console.error(`Errore recupero film per l'anno ${req.params.year}:`, error.message);
        // Se il backend risponde 404 (nessun film), lo passiamo al frontend
        if (error.response && error.response.status === 404) {
            return res.status(404).json({ content: [] });
        }
        next(error);
    }
}); */





router.get('/api/genres/all', async function(req, res, next) {
    try {
        const response = await axios.get('http://localhost:8080/genres/all');
        res.json(response.data);
    } catch (error) {
        res.status(500).json({ error: 'Error while loading genres!' });
    }
})

/*get genres*/
router.get('/genres', function(req, res, next) {
    res.render('genres', {
        title: "Sfoglia per Genere",
    });
});


/* GET Movie Detail Page */
router.get('/movie/:id', async function(req, res, next) {
    const movieId = req.params.id;

    try {
        // Chiede i dettagli al Backend Java
        const response = await axios.get(`http://localhost:8080/movies/${movieId}`);
        let movieData = response.data;
        if (movieData.content && movieData.content.length > 0) {
            movieData = movieData.content[0];
        } else if (movieData.content && movieData.content.length === 0) {
            // Gestione film non trovato
            throw new Error("Film non trovato");
        }

        res.render('movie_detail', {
            title: movieData.movieTitle,
            movie: movieData
        });
    } catch (error) {
        console.error("Errore recupero film:", error.message);
        res.render('error', { message: "Impossibile caricare il film.", error: { status: 404 } });
    }
});



router.get('/api/reviews/film/:title', async (req, res) => {
    try {
        const title = req.params.title;
        const { page, size } = req.query;


        const response = await axios.get(`http://localhost:3001/film/paginated/${encodeURIComponent(title)}`, {
            params: { page, size }
        });


        res.json(response.data);
    } catch (error) {
        console.error("Errore nel Gateway del Server Centrale:", error.message);
        res.status(500).json({ error: "Impossibile recuperare le recensioni dal server dedicato" });
    }
});

router.get('/api/movies/related/:movieId', async function(req, res, next) {
    try {
        const movieId = req.params.movieId;
        const { page, size } = req.query;

        const response = await axios.get(`http://localhost:8080/movies/related`, {
            params: { movieId : movieId, page : page || 0, size : size || 6 }

        });

        res.json(response.data);

    } catch (error) {
        console.error("Errore durante il caricamento dei film correlati: ", error.message);
        res.status(500).json({ error: 'Errore durante il caricamento dei film correlati!' })
    }
})






module.exports = router;