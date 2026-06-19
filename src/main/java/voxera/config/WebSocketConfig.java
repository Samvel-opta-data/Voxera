package voxera.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.HandshakeInterceptor;
import voxera.service.MessageService;
import voxera.service.PresenceService;
import voxera.websocket.VoxeraWebSocketHandler;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {

    private final PresenceService presenceService;
    private final MessageService messageService;
    private final voxera.repository.UserRepository userRepository;

    public WebSocketConfig(PresenceService presenceService, MessageService messageService, voxera.repository.UserRepository userRepository) {
        this.presenceService = presenceService;
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(new VoxeraWebSocketHandler(presenceService, messageService, userRepository), "/voxera-ws")
                .setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                                   @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {
                        String query = request.getURI().getQuery();
                        if (query != null) {
                            for (String param : query.split("&")) {
                                String[] kv = param.split("=", 2);
                                if (kv.length == 2 && "username".equals(kv[0])) {
                                    attributes.put("username", kv[1]);
                                }
                            }
                        }
                        return true;
                    }

                    @Override
                    public void afterHandshake(@NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response,
                                               @NonNull WebSocketHandler wsHandler, Exception exception) {}
                });
    }
}