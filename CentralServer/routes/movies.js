let express = require('express');
let router = express.Router();
const moviesController = require('../controllers/moviesController');

let {validateMoviesPageOptions, validateMovieTitle, validateMovieId} = require('../middlewares/validation');

/**
 * @swagger
 * /:
 *   get:
 *     summary: Gets a list of movies
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: query
 *         name: page
 *         required: false
 *         schema:
 *           type: integer
 *           minimum: 1
 *         description: Page number for pagination.
 *       - in: query
 *         name: size
 *         required: false
 *         schema:
 *           type: integer
 *           minimum: 1
 *         description: Number of movies per page.
 *       - in: query
 *         name: sort
 *         required: false
 *         schema:
 *           type: string
 *         description: Field to sort movies by (e.g., 'title', 'popularity').
 *       - in: query
 *         name: order
 *         required: false
 *         schema:
 *           type: string
 *           enum: [asc, desc]
 *         description: Sort order ('asc' for ascending, 'desc' for descending).
 *     responses:
 *       200:
 *         description: HTML showing the list of movies returned successfully.
 *       400:
 *         description: Invalid query parameters.
 *       500:
 *         description: Internal server error.
 */
router.get('/', validateMoviesPageOptions, moviesController.getMovies)

/**
 * @swagger
 * /{movieId}/details:
 *   get:
 *     summary: Gets details for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: Movie details as an HTML page.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/details', moviesController.getMovieDetails);

/**
 * @swagger
 * /{movieId}/categories:
 *   get:
 *     summary: Gets categories for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of categories for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/categories', moviesController.getCategoriesByMovieId);

/**
 * @swagger
 * /{movieId}/critics:
 *   get:
 *     summary: Gets critics for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of critics for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/critics', moviesController.getCriticsByMovieId);

/**
 * @swagger
 * /{movieId}/cast:
 *   get:
 *     summary: Gets cast for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of cast members for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/cast', moviesController.getCastByMovieId);

/**
 * @swagger
 * /{movieId}/crew:
 *   get:
 *     summary: Gets crew for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of crew members for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/crew', moviesController.getCrewByMovieId);

/**
 * @swagger
 * /{movieId}/studios:
 *   get:
 *     summary: Gets studios for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of studios for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/studios', moviesController.getStudiosByMovieId);

/**
 * @swagger
 * /{movieId}/releases:
 *   get:
 *     summary: Gets release dates for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of release dates for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/releases', moviesController.getReleases);

/**
 * @swagger
 * /{movieId}/suggested:
 *   get:
 *     summary: Gets suggested movies based on a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of suggested movies.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
router.get('/:movieId/related', moviesController.getRelatedMovies);

/**
 * @swagger
 * /{movieId}/reviews:
 *   get:
 *     summary: Gets reviews for a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: List of reviews for the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
//router.get('/:movieId/reviews', moviesController.getReviewsByMovieId);

/**
 * @swagger
 * /{movieId}:
 *   get:
 *     summary: Gets general information about a specific movie
 *     tags:
 *       - Movies
 *     parameters:
 *       - in: path
 *         name: movieId
 *         required: true
 *         schema:
 *           type: string
 *         description: The unique identifier for the movie.
 *     responses:
 *       200:
 *         description: General information about the movie.
 *       400:
 *         description: Invalid movieId parameter.
 *       404:
 *         description: Movie not found.
 *       500:
 *         description: Internal server error.
 */
router.get('/:movieId', moviesController.getMovie);

module.exports = router;