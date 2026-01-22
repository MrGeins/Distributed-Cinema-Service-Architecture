const resError = require("../utils/resError");

/**
 * Validates query parameters for movies page options.
 * Checks for valid sort, order, and genres values.
 * Calls next with error if validation fails.
 *
 * @function validateMoviesPageOptions
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateMoviesPageOptions = (req, res, next) => {
    const {sort, order, genres} = req.query;
    const validSortOptions =['popular', 'newest', 'movie'];
    const validOrderOptions = ['ASC', 'DSC'];
    const validGenres=['Comedy', 'Adventure', 'Thriller', 'Drama', 'Science Fiction', 'Action', 'Music', 'Romance', 'History', 'Crime', 'Animation', 'Mystery', 'Horror', 'Family', 'Fantasy', 'War', 'Western', 'TV Movie', 'Documentary'];

    if (sort && !validSortOptions.includes(sort)) {
        const error = new Error(`Invalid sort option: ${sort}`);
        error.status = 400;
        return next(error);
    }

    if (order && !validOrderOptions.includes(order)) {
        const error = new Error(`Invalid order option: ${order}`);
        error.status = 400;
        return next(error);
    }

    console.log(!Array.isArray(genres));
    if (genres && !Array.isArray(genres)) {
        req.query.genres = [genres];
        console.log(genres + "\n");
    }
    if (genres && Array.isArray(genres)) {
        genres.every(genre => {
            console.log(genre + "\n");
        })
    }
    next();
}

/**
 * Validates that the movie_title query parameter is present.
 * Calls next with error if missing.
 *
 * @function validateMovieTitle
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateMovieTitle = (req, res, next) => {
    const { movie_title } = req.query;

    if (!movie_title) {
        const error = new Error("Movie title is required");
        error.status = 400;
        return next(error);
    }
    next();
}

/**
 * Validates that the searchValue query parameter is present.
 * Calls next with error if missing.
 *
 * @function validateSearchParams
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateSearchParams = (req, res, next) => {
    const { searchValue } = req.query;

    if (!searchValue) {
        const error = new Error("Search value is required");
        error.status = 400;
        return next(error);
    }
    next();
}

/**
 * Validates that the actor name parameter is present in the route.
 * Calls next with error if missing.
 *
 * @function validateActorName
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateActorName = (req,res,next)=>{
    const actorName = req.params.name;

    if (!actorName) {
        const error = new Error("Actor name is required");
        error.status = 400;
        return next(error);
    }
}

/**
 * Validates that email and password are present in the request body.
 * Calls next with error if missing.
 *
 * @function validateLogin
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateLogin = (req, res, next) => {
    const {username, password} = req.body;

    if (!username || !password) {
        const error = new Error("Username and password are required");
        error.status = 400;
        return next(error);
    }
    next();
}

/**
 * Validates registration fields in the request body.
 * Renders error if any field is missing, passwords do not match, or password is too short.
 *
 * @function validateRegister
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateRegister = (req, res, next) => {
    const {username, password} = req.body;

    if (!username || !password) {
        return res.render('users/register', { title: 'Registration', error: 'Username, password are required' });
    }

    if(password.length < 3){
        return res.render('users/register', { title: 'Registration', error: 'Password must be at least 3 digits' });
    }
    next();
}

/**
 * Validates that movieId is present in req.body.params.
 * Calls next with error if missing.
 *
 * @function validateMovieId
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateMovieId = (req, res, next) => {
    const { movieId } = req.body.params;

    if (!movieId) {
        const error = new Error("Movie ID is required");
        error.status = 400;
        return next(error);
    }
    next();
}

/**
 * Validates that the review ID (_id) is present in route params.
 * Calls next with error if missing.
 *
 * @function validateIdReview
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateIdReview = (req, res, next) => {
    const { _id } = req.params;
    if (!_id) {
        const error = new Error("Review ID is required");
        error.status = 400;
        return next(error);
    }
    next();
}

/**
 * Validates required fields for updating a review in the request body.
 * Calls next with error if any field is missing.
 *
 * @function validateReviewUpdate
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateReviewUpdate = (req, res, next) => {
    const { _id, reviewTitle, reviewText, rating }= req.body;
    if (!_id || !reviewTitle || !reviewText || !rating) {
        const error = new Error("All fields are required for review update");
        error.status = 400;
        return next(error);
    }
}

/**
 * Validates required fields for creating a new review in the request body.
 * Calls next with error if any field is missing.
 *
 * @function validateNewReview
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateNewReview = (req, res, next) => {
    const { movieId, movieTitle, username, reviewTitle, reviewText, rating } = req.body;
    if (!movieId || !movieTitle || !username || !reviewTitle || !reviewText || !rating) {
        const error = new Error("All fields are required for new review");
        error.status = 400;
        return next(error);
    }
    next();
}

/**
 * Validates that username is present in the request body.
 * Calls next with error if missing.
 *
 * @function validateUsername
 * @param {import('express').Request} req
 * @param {import('express').Response} res
 * @param {import('express').NextFunction} next
 */
const validateUsername = (req, res, next) => {
    const { username } = req.body;
    if (!username) {
        const error = new Error("Username is required");
        error.statusCode = 400; // Set the status code
        return next(error);
    }
    next();
}

module.exports = {
    validateReviewUpdate,
    validateMovieId,
    validateActorName,
    validateMoviesPageOptions,
    validateMovieTitle,
    validateSearchParams,
    validateLogin,
    validateRegister,
    validateIdReview,
    validateNewReview,
    validateUsername,
}