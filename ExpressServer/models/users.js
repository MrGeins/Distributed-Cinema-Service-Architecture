const mongoose = require('mongoose');
const Schema = mongoose.Schema;

/**
 * User schema for storing user credentials and information.
 * @typedef {Object} User
 * @property {string} username - Unique username for the user.
 * @property {string} email - Unique email address, must be valid format.
 * @property {string} password - User password, minimum 6 characters.
 * @property {Date} createdAt - Timestamp when the user was created.
 * @property {Date} updatedAt - Timestamp when the user was last updated.
 */

const userSchema = new Schema({
    username: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    role: {
        type: String,
        enum: ['fan', 'expert'],
        default: 'fan'
    }
}, {
    timestamps: true // Add createdAt e updatedAt automatically
});

module.exports = mongoose.model('User', userSchema);