const mongoose = require('mongoose');

/**
 * Message Schema for storing chat messages.
 * @typedef {Object} Message
 * @property {String} room - The chat room identifier.
 * @property {String} username - The username of the message sender.
 * @property {String} textMessage - The content of the message.
 * @property {String} role - The role of the user (default is 'fan').
 * @property {Date} timestamp - The time the message was sent (default is current time).
 */
const messageSchema = new mongoose.Schema({
    room: { type: String, required: true },
    username: { type: String, required: true },
    textMessage: { type: String, required: true },
    role: { type: String, default: 'fan' },
    timestamp: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Message', messageSchema);