let currentUsername = document.getElementById("authUsername")?.value || "Samvel";
console.log("Aktiver Chat-User:", currentUsername);

const socket = new SockJS(`/voxera-ws?username=${encodeURIComponent(currentUsername)}`);

const chatMessages = document.querySelector(".chat-messages");
const input = document.querySelector(".chat-input");
const button = document.querySelector(".send-button");

socket.onopen = function () {
    console.log('Lite-WebSocket-Verbindung erfolgreich aufgebaut!');
};

socket.onmessage = function (event) {
    try {
        const message = JSON.parse(event.data);

        // Nur normale Chat-Nachrichten verarbeiten und anzeigen
        if (message.type === 'chat') {
            displayMessage(message);
        }
    } catch (e) {
        console.error("Fehler beim Lesen der Nachricht:", e);
    }
};

function displayMessage(message) {
    const messageElement = document.createElement("div");

    messageElement.classList.add("message", message.from === currentUsername ? "own" : "other");

    const sender = message.from ? message.from : "Unknown";
    const textContent = message.content ? message.content : "";

    messageElement.textContent = sender + ": " + textContent;
    chatMessages.appendChild(messageElement);
    chatMessages.scrollTop = chatMessages.scrollHeight; // Automatisch nach unten scrollen
}

button.addEventListener("click", sendMessage);
input.addEventListener("keypress", (event) => { if (event.key === "Enter") sendMessage(); });

function sendMessage() {
    const text = input.value.trim();
    if (text === "") return;

    const chatMessage = {
        type: 'chat',
        from: currentUsername,
        content: text,
        roomId: 'global'
    };

    socket.send(JSON.stringify(chatMessage));
    input.value = ""; // Textfeld leeren
}