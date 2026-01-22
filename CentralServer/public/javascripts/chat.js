const socket = io();

// Gestione Identità Utente
if (!sessionStorage.getItem('chat_user_id')) {
    const newId = 'user_' + Math.random().toString(36).substring(2, 11);
    sessionStorage.setItem('chat_user_id', newId);
}
const myId = sessionStorage.getItem('chat_user_id');

let actualOpenedRoom = null;

// Ascolto Messaggi dal Server
socket.on('newMessage', data => {
    const messagesList = document.getElementById('messages');
    const item = document.createElement('li');

    item.classList.add('message-bubble');

    if (data.username === currentUsername) {
        item.classList.add('message-sent');
    } else {
        item.classList.add('message-received');
    }

    if (data.role === 'expert') {
        item.classList.add('expert');
    } else {
        item.classList.add('fan');
    }

    const roleBadge = data.role === 'expert' ? ' <span class="badge bg-warning text-dark">🛡️ EXPERT</span>' : '';

    item.innerHTML = `
        <div class="message-username ${data.role === 'expert' ? 'expert' : ''}">
            ${data.username}${roleBadge}
        </div>
        <div class="message-text">${data.textMessage}</div>
    `;

    messagesList.appendChild(item);
    messagesList.scrollTop = messagesList.scrollHeight;
});

socket.on('fetchMessages', data => {
    const messagesList = document.getElementById('messages');
    if (!messagesList) return;
    console.log("OGGETTO RICEVUTO DAL SERVER:", data);
    messagesList.innerHTML = '';
    console.log("RISPOSTA DEL SERVER: " + data.response)
    const messages = (data.response && data.response.messages) ? data.response.messages : [];
    if (messages.length > 0) {

        messages.reverse().forEach(msg => renderMessage(msg));
        //renderSystemMessage("Cronologia caricata.");
    } else {
        renderSystemMessage("Nessun messaggio precedente.");
    }
});

socket.on('hasJoined', data => {
    renderSystemMessage(data.textMessage);
});

socket.on('hasDisconnect', data => {
    renderSystemMessage(data.message);
});

// Apertura Chat
window.openChat = function (filmTitle, filmId) {
    const room = `film:${filmId}`;

    if (actualOpenedRoom && actualOpenedRoom !== room) {
        socket.emit('leave room', actualOpenedRoom);
    }

    actualOpenedRoom = room;

    document.getElementById('chat-card').style.display = 'block';
    document.getElementById('chat-window-title').innerText = filmTitle;

    const messagesList = document.getElementById('messages');
    if (messagesList) {
        messagesList.innerHTML = '<p class="text-muted small">Loading...</p>';
    }

    socket.emit('join', { room: room, username: currentUsername || 'Guest' });
};

// Invio Messaggi (Inizializzazione UI)
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById('chat-form');
    const input = document.getElementById('chat-message-input');
    const closeBtn = document.getElementById('close-chat');

    if (form) {
        form.addEventListener('submit', e => {
            e.preventDefault();
            const text = input.value.trim();
            if (text && actualOpenedRoom) {
                socket.emit('chat', {
                    room: actualOpenedRoom,
                    username: myId,
                    textMessage: text
                });
                input.value = '';
            }
        });
    }

    if (closeBtn) {
        closeBtn.onclick = () => {
            document.getElementById('chat-card').style.display = 'none';
        };
    }
});

function renderMessage(msgData) {
    const container = document.getElementById('messages');
    if (!container) return;


    const text = msgData.textMessage || msgData.message|| msgData.text || "";
    //console.log("MESSAGGI PRECEDENTI: " + text)
    const sender = msgData.username || "Anonymous";
    const role = msgData.role || "fan";

    console.log("Current Username: ", currentUsername);
    const isMine = (currentUsername !== "" && sender === currentUsername);

    const li = document.createElement('li');
    li.className = "chat-message-item"; 
    li.style.display = "flex";
    li.style.flexDirection = "column";
    
    const bubble = document.createElement('div');
    
    bubble.className = `message-bubble ${isMine ? 'message-sent' : 'message-received'} ${role}`;
    bubble.innerHTML = `
        <div class="message-username ${role}">
            ${isMine ? 'Tu' : sender}
        </div>
        <div class="message-text">
            ${text}
        </div>
    `;
    
    li.appendChild(bubble);
    container.appendChild(li);
    container.scrollTop = container.scrollHeight;
}

// Funzione per i messaggi di sistema
function renderSystemMessage(text) {
    const container = document.getElementById('messages');
    if (!container || !text) return;

    const li = document.createElement('li');
    li.className = "text-center my-3 w-100";
    li.style.listStyle = "none";
    li.style.clear = "both";

    li.innerHTML = `
        <span class="badge rounded-pill" style="background-color: rgba(255,255,255,0.1); color: #aaa; font-size: 0.7rem; font-weight: normal; padding: 5px 12px; border: 1px solid rgba(255,255,255,0.05);">
            ${text}
        </span>
    `;

    container.appendChild(li);


    container.scrollTop = container.scrollHeight;
}