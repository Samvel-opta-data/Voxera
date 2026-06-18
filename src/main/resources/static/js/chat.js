const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

const chatMessages = document.querySelector(".chat-messages");
const input = document.querySelector(".chat-input");
const button = document.querySelector(".send-button");

stompClient.connect({}, function (frame) {
    console.log('Verbunden mit WebSocket: ' + frame);

    // Wir abonnieren den globalen Chat-Kanal
    stompClient.subscribe('/topic/messages', function (messageOutput) {
        const message = JSON.parse(messageOutput.body);
        displayMessage(message);
    });
}, function(error) {
    console.error('WebSocket Verbindungsfehler: ' + error);
});

function displayMessage(message) {
    const messageElement = document.createElement("div");

    // Prüfen, ob die Nachricht von mir selbst kommt
    if (message.from === currentUsername) {
        messageElement.classList.add("message", "own");
    } else {
        messageElement.classList.add("message", "other");
    }

    messageElement.textContent = message.from + ": " + message.content;
    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight; 
}

button.addEventListener("click", () => {
    sendMessage();
});

input.addEventListener("keypress", (event) => {
    if (event.key === "Enter") {
        sendMessage();
    }
});

function sendMessage() {
    const text = input.value.trim();
    if (text === "") return;

    const chatMessage = {
        type: 'CHAT',
        from: currentUsername,
        content: text,
        timestamp: Date.now().toString()
    };

    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    input.value = "";
}