const chatService = require('../services/chatService');

exports.init = function(io) {
    io.on('connection', function(socket) {
        try {
            console.log('A user connected:', socket.id);

            handleRoomEvents(socket);
            handleChatEvents(socket, io);
            handleDisconnectionEvents(socket, io);

        } catch (error) {
            console.error("Socket connection error:", error.message);
        }
    });
};

async function handleRoomEvents(socket) {
    socket.on('join', async function(data) { 
        const { room, username } = data;
        if (!room) return;

        socket.currentRoom = room;
        socket.currentUsername = username;
        socket.join(room);

        try {
            const response = await chatService.getMessagesByRoom(room); 
            socket.emit('fetchMessages', { response: response });
        } catch (error) {
            console.error("Errore on fetching history:", error);
            socket.emit('fetchMessages', { response: [] });
        }
    });
}

function handleChatEvents(socket, io) {
    socket.on('chat', async function({ room, textMessage }) {
        socket.handshake.session.reload(async (err) => {
            const user = socket.handshake.session.user;

            if (!user) {
                console.error("[SECURITY] Attempt to send message from a non-logged in user.");
                return;
            }

            if (!room || !textMessage) return;
            console.log("[DEBUG] Socket user session:", socket.handshake.session.user);

            //const user = socket.handshake.session.user;
            const username = user.username;
            const role = user.role || 'fan';

            const newMessage = {
                room: room,
                username: username,
                textMessage: textMessage,
                role: role,
                timestamp: new Date()
            };

            try {
                await chatService.saveMessage(newMessage);
                io.to(room).emit('newMessage', newMessage);
            } catch (error) {
                console.error("Error on saving message:", error);
            }

        })

    });
}

function handleDisconnectionEvents(socket, io) {
    socket.on('disconnect', function() {
        console.log('User disconnected:', socket.id);

        const room = socket.currentRoom;
        const user = socket.handshake.session.user;
        const username = user ? user.username : 'Anonymous';

        if (room) {
            io.to(room).emit('hasDisconnect', {
                message: `${username} disconnected from the chat.`
            });
        }
    });
}
