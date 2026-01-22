const express = require('express');
const router = express.Router();
const messageController = require('../controllers/messagesController');

router.post('/create', messageController.createMessage);

router.post('/get', messageController.getMessages);

module.exports = router;