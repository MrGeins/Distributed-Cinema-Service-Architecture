let express = require('express');
let router = express.Router();
const axios = require('axios');
const actorsController = require("../controllers/actorsController");

/**
 * ADD SWAGGER DOCUMENTATION
 */

router.get("/", function (req, res, next) {
    res.render('partials/actors_search', {
        title: "Actors Catalogue"
    });
} )

router.get("/search", async function (req, res, next) {
    try {
        const query = req.query.name;

        const response = await axios.get(`http://localhost:8080/actors/info/${encodeURIComponent(query)}`, {
            params: {
                searchValue: query,
                page: 0,
                size: 12
            }
        });
        res.json(response.data);
        console.log(response.data);
    } catch (error) {
        res.status(500).json({error: "Error while searching for actors.hbs!"});
    }
})

router.get("/list", async function (req, res, next) {
    try {
        const {searchValue, page, size} = req.query;
        const response = await axios.get(`http://localhost:8080/actors/list`, {
            params: {
                searchValue : searchValue,
                page: page || 0,
                size: size || 12

            }
        });

        res.json(response.data);
    } catch (error) {
        console.error("Error on Java proxy:", error.message);
        res.status(500).json({error: "Error while fetching list of actors!"});
    }

})

router.get("/:name", actorsController.getActors);

module.exports = router;