const input = document.querySelector(".chat-input");
const button = document.querySelector(".send-button");

button.addEventListener("click", async () => {
    const text = input.value.trim();

    if (text === "") {
        return;
    }

    await fetch("/api/chat/send", {
        "method": "POST",

        "headers": {
            "Content-Type": "application/json"
        },
        "body": JSON.stringify({
            message: text,
            timestamp: Date.now()
        })

    });

    input.value = "";
});