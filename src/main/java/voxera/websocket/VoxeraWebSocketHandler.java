package voxera.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import voxera.realtime.RealtimeMessage;
import voxera.service.PresenceService;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class VoxeraWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final PresenceService presenceService;

    public VoxeraWebSocketHandler(PresenceService presenceService) {
        this.presenceService = presenceService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = resolveUsername(session);
        presenceService.register(session, username);
        broadcastPresence();
        send(session, systemMessage("system", "voxera", username, "connected"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RealtimeMessage inbound = objectMapper.readValue(message.getPayload(), RealtimeMessage.class);
        String sender = resolveSender(session, inbound);

        switch (safeType(inbound.type())) {
            case "chat" -> handleChat(sender, inbound);
            case "call.invite", "call.offer" -> forwardToTarget(sender, inbound, "call.invite");
            case "call.answer" -> forwardToTarget(sender, inbound, "call.answer");
            case "call.ice" -> forwardToTarget(sender, inbound, "call.ice");
            case "call.accept" -> forwardToTarget(sender, inbound, "call.accept");
            case "call.reject" -> forwardToTarget(sender, inbound, "call.reject");
            case "call.hangup" -> forwardToTarget(sender, inbound, "call.hangup");
            case "presence.request" -> send(session, objectMessage("presence.update", "voxera", sender, null, null, null, null, null, "online", Instant.now().toString(), presenceSnapshotPayload()));
            default -> send(session, systemMessage("system", "voxera", sender, "unsupported message type"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        presenceService.unregister(session);
        broadcastPresence();
    }

    private void handleChat(String sender, RealtimeMessage inbound) throws IOException {
        RealtimeMessage outbound = objectMessage(
                "chat",
                sender,
                inbound.to(),
                inbound.roomId() == null || inbound.roomId().isBlank() ? "global" : inbound.roomId(),
                inbound.content(),
                inbound.media(),
                null,
                null,
                "delivered",
                Instant.now().toString(),
                null
        );

        if (inbound.to() != null && !inbound.to().isBlank()) {
            sendToUser(inbound.to(), outbound);
        } else {
            broadcast(outbound);
        }
    }

    private void forwardToTarget(String sender, RealtimeMessage inbound, String type) throws IOException {
        if (inbound.to() == null || inbound.to().isBlank()) {
            return;
        }
        RealtimeMessage outbound = objectMessage(
                type,
                sender,
                inbound.to(),
                inbound.roomId(),
                inbound.content(),
                inbound.media(),
                inbound.sdp(),
                inbound.candidate(),
                inbound.status(),
                Instant.now().toString(),
                inbound.payload()
        );
        if (!sendToUser(inbound.to(), outbound)) {
            sendToUser(sender, systemMessage("system", "voxera", sender, "user is offline"));
        }
    }

    private void broadcastPresence() throws IOException {
        RealtimeMessage payload = objectMessage(
                "presence.update",
                "voxera",
                null,
                null,
                null,
                null,
                null,
                null,
                "online",
                Instant.now().toString(),
                presenceSnapshotPayload()
        );
        broadcast(payload);
    }

    private Map<String, Object> presenceSnapshotPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("users", presenceService.snapshot());
        payload.put("count", presenceService.snapshot().size());
        return payload;
    }

    private RealtimeMessage systemMessage(String type, String from, String to, String content) {
        return objectMessage(type, from, to, null, content, null, null, null, "info", Instant.now().toString(), null);
    }

    private RealtimeMessage objectMessage(String type, String from, String to, String roomId, String content, String media, String sdp, String candidate, String status, String timestamp, Map<String, Object> payload) {
        return new RealtimeMessage(type, from, to, roomId, content, media, sdp, candidate, status, timestamp, payload);
    }

    private String resolveUsername(WebSocketSession session) {
        Object value = session.getAttributes().get("username");
        if (value != null && !value.toString().isBlank()) {
            return value.toString();
        }
        if (session.getPrincipal() != null && session.getPrincipal().getName() != null && !session.getPrincipal().getName().isBlank()) {
            return session.getPrincipal().getName();
        }
        return "guest-" + session.getId().substring(0, Math.min(session.getId().length(), 8));
    }

    private String resolveSender(WebSocketSession session, RealtimeMessage inbound) {
        if (inbound.from() != null && !inbound.from().isBlank()) {
            return inbound.from();
        }
        return resolveUsername(session);
    }

    private String safeType(String type) {
        return type == null ? "" : type.trim().toLowerCase();
    }

    private boolean sendToUser(String username, RealtimeMessage message) throws IOException {
        boolean delivered = false;
        for (WebSocketSession target : presenceService.getSessions(username)) {
            send(target, message);
            delivered = true;
        }
        return delivered;
    }

    private void broadcast(RealtimeMessage message) throws IOException {
        for (WebSocketSession target : presenceService.allSessions()) {
            send(target, message);
        }
    }

    private void send(WebSocketSession session, RealtimeMessage message) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }
    }
}
