let rottenReviews = require('../models/rotten');
const reviews = require("../models/review");

exports.listOfRottenReviews = async function () {
    try {
        const allRottenReviews = await rottenReviews.find().limit(10);
        console.log("Found", rottenReviews.length, "rotten reviews");
        return allRottenReviews;
    } catch (error) {
        throw error;
    }
}

exports.search = async function (query) {
    try {
        const {
            title,
            minScore,
            maxScore,
            startDate,
            endDate,
            topCritic,
            reviewType,
        } = query;

        const filter = {};

        // Title filter (case insensitive)
        if (title) {
            filter.movie_title = { $regex: new RegExp(title, "i") };
        }

        // Score range filter
        if (minScore || maxScore) {
            filter.review_score = {};
            if (minScore) filter.review_score.$gte = parseFloat(minScore);
            if (maxScore) filter.review_score.$lte = parseFloat(maxScore);
        }

        // Date range filter
        if (startDate || endDate) {
            filter.review_date = {};
            if (startDate) filter.review_date.$gte = new Date(startDate);
            if (endDate) filter.review_date.$lte = new Date(endDate);
        }

        // Filter: top_critic boolean
        if (topCritic === "true") filter.top_critic = "true";
        if (topCritic === "false") filter.top_critic = "false";

        // Filter: review_type
        if (reviewType) {
            filter.review_type = reviewType;
        }

        return await rottenReviews.find(filter).limit(30);
    } catch (err) {
        throw err;
    }
};


exports.findByTitlePaginated = async function (title, page, size) {
    try {
        const limit = parseInt(size) || 10;
        const skip = (parseInt(page) || 0) * limit;

        const fuzzyTitle = title
            .replace(/[.*+?^${}()|[\]\\]/g, '\\$&')
            .replace(/[:&]/g, '.*');

        const filter = {
            movie_title: { $regex: new RegExp(fuzzyTitle) }
        };

        // USA IL MODELLO ROTTEN!
        const results = await rottenReviews.find(filter)
            .sort({ review_date: -1, _id: 1 })
            .skip(skip)
            .limit(limit);

        const total = await rottenReviews.countDocuments(filter);

        return {
            content: results,
            totalPages: Math.ceil(total / limit),
            isLast: skip + results.length >= total
        };
    } catch (error) {
        throw error;
    }
};