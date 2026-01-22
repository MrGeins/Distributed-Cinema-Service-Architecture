const mongoose = require('mongoose');

/**
 * Rotten schema for storing critic reviews from Rotten Tomatoes.
 * @typedef {Object} Rotten
 * @property {string} rotten_tomatoes_link - The link to the Rotten Tomatoes review.
 * @property {string} movie_title - The title of the movie.
 * @property {string} critic_name - The name of the critic.
 * @property {boolean} top_critic - Whether the critic is a top critic.
 * @property {string} publisher_name - The name of the publisher.
 * @property {string} review_type - The type of review (e.g., positive/negative).
 * @property {string} review_score - The score given by the critic (0-5).
 * @property {string} review_date - The date of the review.
 * @property {string} review_content - The content of the review.
 */
const rottenSchema = new mongoose.Schema({
    rotten_tomatoes_link:{
        type: String,
        trim:true,
        required: true,
    },
    movie_title:{
        type: String,
        trim:true,
        required: true,
    },
    critic_name:{
        type: String,
        required: true,
        trim:true
    },
    top_critic:{
        type: Boolean,
        required: true,
        trim:true
    },
    publisher_name:{
        type: String,
        required: true,
        trim:true
    },
    review_type:{
        type: String,
        required: true,
        trim:true
    },
    review_score:{
        type: String,
        trim:true,
        required: true,
        min: 0,
        max: 5,
    },
    review_date:{
        type: String,
        required: true,
        trim:true
    },
    review_content:{
        type: String,
        required: true,
        trim:true
    },
}, { collection: 'rotten' });


module.exports = mongoose.models.Rotten || mongoose.model('Rotten', rottenSchema);