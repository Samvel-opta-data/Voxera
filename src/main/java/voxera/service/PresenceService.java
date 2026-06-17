package voxera.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import voxera.realtime.OnlineUserView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PresenceService {

    private final Map<String, Set<WebSocketSession>> sessionsByUser = new ConcurrentHashMap<>();
    private final Map<String, String> userBySessionId = new ConcurrentHashMap<>();

    public void register(WebSocketSession session, String username) {
        sessionsByUser.computeIfAbsent(username, ignored -> ConcurrentHashMap.newKeySet()).add(session);
        userBySessionId.put(session.getId(), username);
    }

    public void unregister(@NonNull WebSocketSession session) {
        String username = userBySessionId.remove(session.getId());
        if (username == null) {
            return;
        }
        Set<WebSocketSession> sessions = sessionsByUser.get(username);
        if (sessions != null) {
            sessions.remove(session);
            if (sessions.isEmpty()) {
                sessionsByUser.remove(username);
            }
        }
    }

    public String getUsername(@NonNull WebSocketSession session) {
        return userBySessionId.get(session.getId());
    }

    public List<OnlineUserView> snapshot() {
        List<OnlineUserView> users = new ArrayList<>();
        sessionsByUser.forEach((username, sessions) -> users.add(new OnlineUserView(username, sessions.size())));
        users.sort((left, right) -> left.username().compareToIgnoreCase(right.username()));
        return Collections.unmodifiableList(users);
    }

    public Set<WebSocketSession> getSessions(@NonNull String username) {
        return sessionsByUser.getOrDefault(username, Set.of());
    }

    public Set<WebSocketSession> allSessions() {
        Set<WebSocketSession> result = ConcurrentHashMap.newKeySet();
        sessionsByUser.values().forEach(result::addAll);
        return result;
    }

    public boolean isOnline(String username) {
        return sessionsByUser.containsKey(username);
    }
}
