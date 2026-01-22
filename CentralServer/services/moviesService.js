const axios = require('axios');

const SPRING_SERVICE_URL='http://localhost:8080';
const MONGO_SERVICE_URL='http://localhost:3001';

/**
 * Retrieves a list of movie base on provided options
 *
 * @async
 * @function getMovies
 * @param {Object} opt query options for filtering movies
 * @param {string} opt.sort sorting field (e.g., 'popular', 'rating')
 * @param {string} opt.order sorting order ('ASC' or 'DSC')
 * @param {number} opt.page page number for pagination (zero-based)
 * @param {number} opt.size number of results per page
 * @param {string[]} opt.genres list of genres to filter by
 * @return {Promise<Object>} list of movies
 * @throws {Error} if fetching movies fails
 */
exports.getMovies = async (opt) => {
    let {sort, order, page, size, genres, genre, year} = opt;

    try {
        const response = await axios.get(SPRING_SERVICE_URL + "/movies/options", {
            params: {
                sort,
                order,
                page,
                size,
                genres: genres || genre,
                year
            }
        });
        return response.data;
    } catch (err) {
        console.error("Error during movies request:", err.message);
        throw new Error("Failed to fetch movies from server");
    }
}

/**
 * Retrieves a paginated list of movies matching the specified title.
 *
 * @async
 * @function getTitle
 * @param {Object} data query parameters
 * @param {string} data.filmTitle the title of the movie to search for
 * @param {number} data.page the page number to retrieve (zero-based)
 * @param {number} data.size the number of results per page
 * @return {Promise<Object>} paginated list of movies
 * @throws {Error} if fetching movies fails
 */
exports.getTitle = async (data) => {
    let {filmTitle, page, size} = data;
    try {
        const path = `/movies/name/${encodeURIComponent(filmTitle)}`;
        const response = await axios.get(SPRING_SERVICE_URL + path, {params:{page, size}});

        return response.data;
    } catch (err) {
        console.error("Error during movies title request:" + err.message);
        throw new Error("Failed to fetch movies title from server");
    }
}

/**
 * Retrieves a paginated list of movies belonging to a specific genre.
 *
 * @async
 * @function getGenre
 * @param {Object} data query parameters
 * @param {string} data.genre the genre to filter movies by
 * @param {number} data.page the page number to retrieve (zero-based)
 * @param {number} data.size the number of results per page
 * @return {Promise<Object>} paginated list of movies
 * @throws {Error} if fetching movies fails
 */
exports.getGenre = async (data) => {
    let {genre, page, size} = data;
    try {
        const path = `/movies/genre/${encodeURIComponent(genre)}`;
        const response = await axios.get(SPRING_SERVICE_URL + path, {params:{page, size}});

        return response.data;
    } catch (err) {
        console.error("Error during movies genre request:" + err.message);
        throw new Error("Failed to fetch movies genre from server");
    }
}

/**
 * Retrieves a paginated list of movies released in a specific year.
 *
 * @async
 * @function getYear
 * @param {Object} data query parameters
 * @param {string} data.year the year of release to filter movies
 * @param {number} data.page the page number to retrieve (zero-based)
 * @param {number} data.size the number of results per page
 * @return {Promise<Object>} paginated list of movies
 * @throws {Error} if fetching movies fails
 */
exports.getYear = async (data) => {
    let {year, page, size} = data;
    try {
        const path = `/movies/year/${year}`;
        const response = await axios.get(SPRING_SERVICE_URL + path, {params:{page, size}});

        return response.data;
    } catch (err) {
        console.error("Error during movies year request:" + err.message);
        throw new Error("Failed to fetch movies by year from server");
    }
}

/**
 * Retrieves a list of related movies associated with a specific movie ID based on shared themes.
 *
 * @async
 * @function getRelatedMovies
 * @param {Object} data query parameters
 * @param {string} data.movieId the unique identifier of the movie to find related films for
 * @param {number} data.page the page number to retrieve (zero-based)
 * @param {number} data.size the number of results per page
 * @return {Promise<Object>} paginated list of related movies
 * @throws {Error} if fetching related movies fails
 */
exports.getRelatedMovies = async (data) => {
    let {movieId, page, size} = data;
    try {
        const response = await axios.get(SPRING_SERVICE_URL + "/movies/related", {params:{movieId, page, size}});

        return response.data;
    } catch (err) {
        console.error("Error during related movies request:" + err.message);
        throw new Error("Failed to fetch related movies from server");
    }
}

/**
 * Retrieves a paginated list of movies with the specified rating.
 *
 * @async
 * @function getRating
 * @param {Object} data query parameters
 * @param {number} data.rating the rating to filter movies
 * @param {number} data.page the page number
 * @param {number} data.size the number of results per page
 * @return {Promise<Object>} list of movies
 * @throws {Error} if fetching movies fails
 */
exports.getRating = async (data) => {
    let {rating, page, size} = data;
    try {
        const path = `/movies/rating/${rating}`;
        const response = await axios.get(SPRING_SERVICE_URL + path, {params:{page, size}});

        return response.data;
    } catch (err) {
        console.error("Error during movies rating request:", err.message);
        throw new Error("Failed to fetch movies by rating from server");
    }
}

/**
 * Retrieves a list of movies featuring a specific Oscar-winning actor.
 *
 * @async
 * @function getOscarActor
 * @param {Object} data query parameters
 * @param {string} data.actor the name of the Oscar-winning actor
 * @return {Promise<Object>} list of movies
 * @throws {Error} if fetching movies fails
 */
exports.getOscarActor = async (data) => {
    let {actor} = data;
    try {
        const path = `/movies/actor/${encodeURIComponent(actor)}`;
        const response = await axios.get(SPRING_SERVICE_URL + path);

        return response.data;
    } catch (err) {
        console.error("Error during Oscar actor movies request:", err.message);
        throw new Error("Failed to fetch movies by Oscar winner from server");
    }
}