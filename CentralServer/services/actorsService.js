const axios = require('axios');
const path = require("node:path");
const {getActors} = require("./searchService");

const SPRING_SERVICE_URL = 'http://localhost:8080';
const MONGO_SERVICE_URL = 'http://localhost:3001';

/**
 * Retrieves a list of actors matching the DTO request
 *
 * @async
 * @function getActors
 * @param {Object} request - DTO containing the search parameters
 * @returns {Promise<Object>} - List of actors
 * @throws {Error} If fetching actors fails
 */
exports.getActors = async (request) => {
    try {
        const response = await axios.get(SPRING_SERVICE_URL + "/actors/list", {params: {request}});
        return response.data;
    } catch (err) {
        console.error("Error during actors request:" + err.message);
        throw new Error("Failed to fetch actors. from server.");
    }
}

/**
 * Retrieves a list of actors in a specific movie
 *
 * @async
 * @function getActorsByMovieId
 * @param {String} movieId - ID of the movie
 * @param {number} page - Page number for pagination
 * @param {number} size - Number of results per page
 * @returns {Promise<Object>} - List of actors in the movie
 * @throws {Error} If fetching actors in movie fails
 */
exports.getActorsByMovieId = async (movieId, page, size) => {
    try {
        const response = await axios.get(`http://localhost:8080/actors/movie/${movieId}`);
        return response.data;
    } catch (err) {
        console.error("Error during actors in movie request:" + err.message);
        throw new Error("Failed to fetch actors in movie from server.");
    }
}

/**
 * Retrieves detailed information about a specific actor
 *
 * @async
 * @function getActorsDetails
 * @param {String} name - Name of the actor
 * @param {number} page - Page number for pagination
 * @param {number} size - Number of results per page
 * @returns {Promise<Object>} - Actor information
 * @throws {Error} If fetching actor info fails
 */
exports.getActorDetails = async (name, page, size) => {
    try {
        const response = await axios.get(`http://localhost:8080/actors/search/${name}`);
        return response.data;
    } catch (err) {
        console.error("Error during actors info request:" + err.message);
        throw new Error("Failed to fetch actor info from server.");
    }
}

module.exports = {
    getActors,
    /*getActorsByMovieId,
    getActorDetails*/
};

