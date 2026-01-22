
//TO DO: LA ROTTA INTERAGISCE DIRETTAMENTE CON I MODELLI --> NON VA BENE!! DEVE SFRUTTARE IL CONTROLLER

const express = require('express');
const router = express.Router();
const review = require('../models/review');
const reviewController = require('../controllers/reviewsController');
// const connectToMongo  = require('../db/connect');

// GET all the reviews
router.get('/', async (req, res) => {
    try {
        const reviews = await reviewController.listReviews();
        res.json({reviews});
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// GET reviews filtered by film
router.get('/film/:title', async (req, res) => {
    try {
        const reviews = await reviewController.findByTitle(req.params.title);
        res.json({reviews});
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

// GET reviews filtered by the parameter written down
router.get("/search", async (req, res) => {
    try {
        //console.log("FFEDFGFGF0")
        const reviews = await reviewController.search(req.query);
        console.log("FOUND REVIEWS: ", reviews);
        res.json(reviews);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});






module.exports = router;

