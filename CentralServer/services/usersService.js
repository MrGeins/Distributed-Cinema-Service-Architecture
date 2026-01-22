const axios = require('axios');
const {authError, ClientError} = require("../utils/errors");

const SPRING_SERVICE_URL='http://localhost:8080';
const MONGO_SERVICE_URL='http://localhost:3001';

/**
 * Logs in a user by sending credentials to the Postgres service.
 *
 * @async
 * @function login
 * @param {string} username - User's username.
 * @param {string} password - User's password.
 * @returns {Promise<Object>} Axios response from the login endpoint.
 * @throws {authError} If login fails due to invalid credentials or server error.
 */
exports.login = async (username, password) => {
    try {
        return await axios.post(MONGO_SERVICE_URL + "/users/login", { username, password });
    } catch (err) {
        let message;
        switch (err.response.status) {
            case 400:
                message='Username and password are required.';
                break;
            case 401:
            case 404:
                message='Username or password wrong';
                break;
            case 500:
                message='An internal error occurred. Please try again later.';
                break;
            default:
                message='An unexpected error occurred.';
        }
        throw new authError(message,err.response.status);
    }
}

/**
 * Registers a new user by sending details to the Mongo service.
 *
 * @async
 * @function register
 * @param {string} username - Desired username.
 * @param {string} password - User's password.
 * @param {string} role - User's role (fan or expert). Defaults to 'fan'.
 * @returns {Promise<Object>} Axios response from the register endpoint.
 * @throws {authError} If registration fails due to conflict or server error.
 */
exports.register = async (username, password, role) => {
    try {
        return await axios.post(MONGO_SERVICE_URL + "/users/register", {username, password, role: role || 'fan'});
    } catch (error) {
        let message;
        switch (error.response.status) {
            case 500:
                message='An internal server error occurred. Please try again later.';
                break;
            case 409:
                message='Username is already used by someone else.';
                break;
            default:
                message='An unexpected error occurred.'
        }
        throw new authError(message,error.response.status);
    }
}