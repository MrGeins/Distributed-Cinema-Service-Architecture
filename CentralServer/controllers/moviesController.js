const {catchedAsync} = require("../utils");
const moviesService = require("../services/moviesService");
const response = require("../utils/response");
const moviesController = require("./indexController");

let SIZE={
    "critics_size": 12,
    "movie_size": 12,
    "review": 12
}

/**
 * Retrieves a list of movies based on query options and renders the movies page or a partial list.
 *
 * @async
 * @function getMovies
 * @param {import('express').Request} req - Express request object, expects query options for filtering.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching or rendering movies fails.
 */
const getMovies = async (req,res)=>{
    const options = req.query;
    if(options.genres && !Array.isArray(options.genres)){
        options.genres = [options.genres]; // Ensure genres is always an array
    }
    let movies;

    if (req.headers['x-requested-with'] === 'XMLHttpRequest') {
        try{
            movies= await moviesService.getMovies(options);
        }catch (err){
            err.statusCode=500;
            throw err;
        }
        let hasMore = movies.length >= (options.size || SIZE.movie_size); // True if there are more movies to load


        res.render('partials/movies_list', { layout: false, movie_list: movies }, (err, html) => {
            if (err) {
                err.statusCode=500;
                err.message="Error rendering movies list";
                throw err;
            }
            response(res,200,{html, hasMore})
        });
    }else{
        res.render('movies/movies', { title: 'Movies' , hasMore:true});
    }
}

/**
 * Retrieves suggested movies based on a movie ID and renders a partial with similar movies.
 *
 * @async
 * @function getSuggestedMovies
 * @param {import('express').Request} req - Express request object, expects movieId, page, and size in body.params.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching or rendering suggested movies fails. */
const getRelatedMovies = async (req, res) => {
    const {movieId, page = 0, size = SIZE.movie_size} = req.params;
    try {
        const moviesResponse = await moviesService.getRelatedMovies(movieId, page, size);
        let hasMore = moviesResponse.length >= size;

        res.render('partials/relatedMovie', { layout: false, similarMovies: moviesResponse, hasMore });
    } catch (err) {
        console.error("Error during movies request:", err);
        res.status(500).json({ error: "Movies request failed" });
    }
};

/**
 * Retrieves details for a specific movie and renders the movie details page.
 *
 * @async
 * @function getMovie
 * @param {import('express').Request} req - Express request object, expects movieId in params.
 * @param {import('express').Response} res - Express response object.
 */
const getMovie = async (req, res) => {
    const movieId = req.params.movieId;
    const cookieData = req.cookies.userData;
    const userData = cookieData ? JSON.parse(decodeURIComponent(cookieData)) : null;
    res.render('movies/movie', { title: 'Movie',userData,movieId });
};

/** TO DO */
/**
 * Retrieves details and reviews for a specific movie and renders the movie details page.
 *
 * @async
 * @function getMovieDetails
 * @param {import('express').Request} req - Express request object, expects movieId in params.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching movie details fails.
 * */
/*const getMovieDetails = async (req,res)=>{
    try {
        const movieId = req.params.movieId;
        const movieResponse = await moviesService.getMovieDetails(movieId);
        res.render('partials/movieDetails', {layout:false,details: movieResponse});
    } catch (err) {
        err.statusCode=500;
        err.message="Error fetching movie details";
        throw err;
    }
}*/

/**
 * Retrieves categories for a specific movie and renders the categories page.
 *
 * @async
 * @function getCategoriesByMovieId
 * @param {import('express').Request} req - Express request object, expects movieId in params.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching movie categories fails.
 */
/*const getCategoriesByMovieId = async (req, res) => {
    try{
        const movieId = req.params.movieId;
        const categoriesResponse = await moviesService.getMovieCategories(movieId);

        res.render('partials/categoriesList', { layout: false,title: 'Categories', categories: categoriesResponse });
    }catch (err) {
        err.statusCode=500;
        err.message="Error fetching movie categories";
        throw err;
    }
};*/

/**
 * Retrieves critics for a specific movie and renders the critics page.
 *
 * @async
 * @function getCriticsByMovieId
 * @param {import('express').Request} req - Express request object, expects movie_title and page in query.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching or rendering critics fails.
 */
/*const getCriticsByMovieId = async (req, res) => {
    const {movie_title, page=0} = req.query;
    console.log(req.query);
    let criticsResponse;
    try{
        criticsResponse= await moviesService.getCritics({movie_title, size:SIZE.critics_size, page:(page ||0)} );
    }catch (err){
        err.statusCode=500;
        throw err;
    }
    let hasMore = criticsResponse.rotten_critics.length >= SIZE.critics_size;

    res.render('partials/critics', { layout: false, critics: criticsResponse.rotten_critics }, (err, html)=>{
        if (err) {
            err.statusCode=500;
            err.message="Error rendering critics list";
            throw err;
        }
        response(res,200,{html, hasMore})
    });

};*/

/**
 * Retrieves cast for a specific movie and renders the cast page.
 *
 * @async
 * @function getCastByMovieId
 * @param {import('express').Request} req - Express request object, expects movieId in params and page, size in query.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching movie cast fails.
 */
/*const getCastByMovieId = async (req, res) => {
    try {
        const {movieId} = req.params;
        const { page=0, size=12}=req.query;

        const castResponse = await moviesService.getMovieCast(movieId, page, size);
        let hasMore = castResponse.length >= size;
        res.render('partials/actors_list', {layout:false, title: 'Cast', actors: castResponse, hasMore });
    }catch (err) {
        err.statusCode=500;
        err.message="Error fetching movie cast";
        throw err;
    }
};*/

/**
 * Retrieves crew for a specific movie and renders the crew page.
 *
 * @async
 * @function getCrewByMovieId
 * @param {import('express').Request} req - Express request object, expects movieId in params.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching movie crew fails.
 */
/*const getCrewByMovieId = async (req, res) => {
    try {
        const movieId = req.params.movieId;
        const crewResponse = await moviesService.getMovieCrew(movieId);
        res.render('partials/crewList.hbs', {layout: false, title: 'Crew', crew: crewResponse});
    } catch (err) {
        err.statusCode = 500;
        err.message = "Error fetching movie crew";
        throw err;
    }
};*/

/**
 * Retrieves studios for a specific movie and renders the studios page.
 * @async
 * @function getStudiosByMovieId
 * @param {import('express').Request} req - Express request object, expects movieId in params.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching or rendering studios fails.
 */
/*const getStudiosByMovieId = async (req, res) => {
    try {
        const movieId = req.params.movieId;
        const studiosResponse = await moviesService.getMovieStudios(movieId);
        res.render('partials/studioList', {layout: false, title: 'Studios', studios: studiosResponse});
    } catch (err) {
        err.statusCode = 500;
        err.message = "Error fetching movie studios";
        throw err;
    }
};*/

/**
 * Retrieves new releases based on a movie ID and renders a partial with releases.
 *
 * @async
 * @function getReleases
 * @param {import('express').Request} req - Express request object, expects movieId, page, and size in body.params.
 * @param {import('express').Response} res - Express response object.
 * @throws {Error} 500 - If fetching or rendering releases fails. */
/*const getReleases = async (req, res) => {
    const {movieId} = req.params;
    const { page=0, size=12}=req.query;

    try {
        const releasesResponse = await moviesService.getReleases(movieId, page, size);
        let hasMore = releasesResponse.length >= size;
        res.render('partials/releases', { layout: false, releases: releasesResponse, hasMore:true });
    } catch (err) {
        console.error("Error during movies request:", err);
        res.status(500).json({ error: "Movies request failed" });
    }
};*/

/**
 * Retrieves reviews for a specific movie and renders them as a partial.
 *
 * @async
 * @function getReviewByMovieId
 * @param {import('express').Request} req - Express request object, expects `movieId`, `page`, and `size` in body.params.
 * @param {import('express').Response} res - Express response object.
 */
/*const getReviewsByMovieId = async (req,res) =>{
    const { movieId} = req.params;
    const {page=0, size=SIZE.review} = req.query;
    let reviews, hasMore;
    try{
        reviews = await moviesService.getReviews(movieId, page, size);
        console.log(reviews.reviews);
        hasMore = reviews.reviews.length >= SIZE.review;

        console.log(hasMore, reviews.reviews.length, SIZE.review);
    }catch (err){
        err.statusCode=500;
        throw err;
    }
    return res.render('partials/reviews', { layout: false, title: 'Dashboard', reviews, hasMore });
};*/

module.exports={
    getMovies: catchedAsync(getMovies),
    getRelatedMovies: catchedAsync(getRelatedMovies),
    getMovie: catchedAsync(getMovie),
    //getMovieDetails: catchedAsync(getMovieDetails),
    //getReleases: catchedAsync(getReleases),
    //getCategoriesByMovieId: catchedAsync(getCategoriesByMovieId),
    //getCriticsByMovieId: catchedAsync(getCriticsByMovieId),
    //getCastByMovieId: catchedAsync(getCastByMovieId),
    //getCrewByMovieId: catchedAsync(getCrewByMovieId),
    //getStudiosByMovieId: catchedAsync(getStudiosByMovieId),
    //getReviewsByMovieId: catchedAsync(getReviewsByMovieId),
}