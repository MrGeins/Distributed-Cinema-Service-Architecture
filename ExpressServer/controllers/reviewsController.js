let reviews = require("../models/review");


exports.listReviews = async function ()  {
    try {
        const topMoviesReviews = await reviews.find().limit(10);
        console.log("found top 10 reviews from Movies: ", topMoviesReviews);
        return topMoviesReviews;
    } catch (error) {
        throw error;
    }
}


exports.findByTitle = async function (title)  {
    try {
        const regex = new RegExp(title, "i");  //CASE INSENSITIVE
        return await reviews.find({movieName: regex});
    } catch (error) {
        throw error;
    }
}


exports.search = async function (query) {
    try {
        const {
            title,
            minScore,
            maxScore,
            // Rimuovi o ignora i parametri non supportati dal modello
        } = query;

        const filter = {};

        // 1. Filtro per Titolo (movieName)
        if (title) {
            filter.movieName = new RegExp(title, "i");
        }

        // 2. Filtro per Punteggio (movieRating)
        if (minScore || maxScore) {
            filter.movieRating = {};


            // La query su Mongoose funzionerà come confronto numerico.
            if (minScore) {
                // $gte: Maggiore o uguale a
                filter.movieRating.$gte = parseFloat(minScore);
            }
            if (maxScore) {
                // $lte: Minore o uguale a
                filter.movieRating.$lte = parseFloat(maxScore);
            }
        }

        // Esegue la query FIND, che ora è corretta e veloce
        return await reviews.find(filter).limit(30);



    } catch (err) {
        console.error("ERRORE NEL CONTROLLER DI RICERCA:", err); // Aggiungi log dettagliato
        // Rilancia un'eccezione con un messaggio chiaro
        throw new Error("Errore durante l'esecuzione della query di ricerca: " + err.message);
    }
};


