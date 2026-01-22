const mongoose = require('mongoose');

const reviewSchema = new mongoose.Schema({
    movieId: String,
    movieName: String,
    movieRating: Number
    },
    { collection: 'reviews'});

module.exports = mongoose.models.Review || mongoose.model('Review', reviewSchema);
