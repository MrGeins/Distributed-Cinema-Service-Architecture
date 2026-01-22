const express = require("express");
const router = express.Router();
const axios = require("axios");

// URL Mongo server
const MONGO_API = "http://localhost:3001";

router.get("/search", async (req, res) => {
    try {
        const response = await axios.get(`${MONGO_API}/reviews/search`, {
            params: req.query,
        });
        res.json(response.data);
    } catch (err) {
        console.error("❌ Proxy search error:", err);
        res.status(500).json({ error: "❌ Error during the search process on the MongoDB server" });
    }
});

module.exports = router;
