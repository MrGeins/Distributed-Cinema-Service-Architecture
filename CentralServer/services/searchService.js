const axios = require('axios');
const path = require("node:path");

const SPRING_SERVICE_URL='http://localhost:8080';
const MONGO_SERVICE_URL='http://localhost:3001';

exports.getMoviesOrActors = async (searchValue, page, size) => {
    try {
        let response;
        response = await axios.post(SPRING_SERVICE_URL + "/search", {searchValue});
        return response;
    } catch (err) {
        console.error("Error during movies request:", err.message);
        throw new Error("Failed to fetch movies from server");
    }
}

exports.getActors = async (searchValue, page, size) => {
    try {
        return await axios.get(SPRING_SERVICE_URL+"/searchActors", {params: {searchValue, page, size}});

    } catch (err) {
        console.error("Error during movies request:", err.message);
        throw new Error("Failed to fetch movies from server");
    }
}

exports.getMovies = async (searchValue, page, size) => {
    try {
        return await axios
    } catch (err) {
        console.error("Error during movies request:", err.message);
    }
}