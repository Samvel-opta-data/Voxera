package voxera.realtime;

import java.util.Map;

public record RealtimeMessage(
        String type,
        String from,
        String to,
        String roomId,
        String content,
        String media,
        String sdp,
        String candidate,
        String status,
        String timestamp,
        Map<String, Object> payload
) {
}
