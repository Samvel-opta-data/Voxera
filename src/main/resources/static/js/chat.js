const input = document.querySelector(".chat-input");
const button = document.querySelector(".send-button");

button.addEventListener("click", () => {
    const text = input.value.trim();

    if(text !== ""){

        // Nachricht senden

        input.value = "";

    }

});