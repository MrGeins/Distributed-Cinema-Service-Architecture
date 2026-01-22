const axios = require('axios');
const MONGO_SERVICE_URL = 'http://localhost:3001';

/**
 * Creates and stores a new message in a chat room
 *
 * @async
 * @function saveMessage
 * @param {Object} messageData - Data of the message to be saved
 * @param {String} messageData.room - Chat room identifier
 * @param {String} messageData.username - Username of the sender
 * @param {String} messageData.textMessage - Content of the message
 * @param {String} messageData.role - Role of the sender (e.g., user, admin)
 * @return {Promise<Object>} Response from the MongoDB service
 */
exports.saveMessage = async (messageData) => {
    try {
        const response = await axios.post(`${MONGO_SERVICE_URL}/messages/create`, {
            room: messageData.room,
            username: messageData.username,
            textMessage: messageData.textMessage,
            role: messageData.role
        });
        return response.data;
    } catch (err) {
        console.error("Error during saveMessage request: " + err.message);
        throw new Error("Failed to save message.");
    }
}

/**
 * Fetches the message history in a chat room
 *
 * @async
 * @function getMessagesByRoom
 * @param {String} room - Chat room identifier
 * @param {number} page - Page number for pagination
 * @return {Promise<Object>} List of messages
 */
exports.getMessagesByRoom = async (room, page = 0) => {
    try {
        // Il tuo secondo controller legge 'room' e 'page' da req.body
        const response = await axios.post(`${MONGO_SERVICE_URL}/messages/get`, {
            room,
            page
        });
        return response.data; // Restituirà { messages: [...] }
    } catch (err) {
        console.error("Error fetching messages: " + err.message);
        return { messages: [] };
    }
}