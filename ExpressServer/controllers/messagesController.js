const Message = require('../models/message');
const SIZES = {
    MESSAGE_PAGE_SIZE: 50,
}


/**
 * Retrieves a paginated list of chat messages for a specific room.
 *
 * This asynchronous Express route handler fetches messages from the database
 * for a given chat room, supporting pagination and sorting by timestamp (most recent first).
 * It expects the `room` identifier in the request body, along with optional `page` and `size`
 * parameters for pagination. If the `room` parameter is missing, it responds with a 400 error.
 * On success, it returns a JSON object containing the array of messages.
 *
 * @async
 * @function getMessages
 * @param {Object} req - Express request object. Expects body to contain:
 *   - room {string} (required): The chat room identifier to fetch messages for.
 *   - page {number} (optional, default 0): The page number for pagination.
 *   - size {number} (optional, default SIZES.MESSAGE_PAGE_SIZE): Number of messages per page.
 * @param {Object} res - Express response object.
 * @returns {Promise<void>}
 * @throws {Error} Responds with 400 if `room` is missing, 200 with messages if successful,
 *   500 for server errors.
 */
module.exports.getMessages = async (req, res) => {
    try{
        const { room , page=0, size=SIZES.MESSAGE_PAGE_SIZE} = req.body;
        if (room != null) {
            const messages = await Message.find({ room })
                .limit(size)
                .skip((page) * (size))
                .sort({ timestamp: -1 })
                .exec();
            res.status(200).json({ messages });
        } else {
            res.status(400).json({ message: "Params are required." });
        }
    } catch (error) {
        res.status(500).json({ message: "An internal server error occurred. Please try again later." + error });
    }
}

/**
 * Creates and stores a new chat message in the database.
 *
 * This asynchronous Express route handler receives message details in the request body,
 * validates required fields (`room`, `username`, `textMessage`), and creates a new message
 * document with the current timestamp. If any required field is missing, it responds with a 400 error.
 * On success, it saves the message and returns the created message object in the response.
 *
 * @async
 * @function createMessage
 * @param {Object} req - Express request object. Expects body to contain:
 *   - room {string} (required): The chat room identifier.
 *   - username {string} (required): The sender's username.
 *   - textMessage {string} (required): The content of the message.
 *   - role {string} (optional, default 'fan'): The role of the sender.
 * @param {Object} res - Express response object.
 * @returns {Promise<void>}
 * @throws {Error} Responds with 400 if any required field is missing, 200 with the created message if successful,
 *   500 for server errors.
 */
module.exports.createMessage = async (req, res) => {
    try {
        const { room, username, textMessage, role } = req.body;

        if (room != null && username != null && textMessage != null) {
            const message = new Message({
                room,
                username,
                textMessage,
                role: role || 'fan',
                timestamp: Date.now()
            });
            await message.save();
            res.status(200).json({ message });
        } else {
            res.status(400).json({ message: "Params are required." });
        }
    } catch (error) {
        res.status(500).json({ message: "An internal server error occurred." + error });
    }
}