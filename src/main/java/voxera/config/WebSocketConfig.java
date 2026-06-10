package voxera.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import voxera.service.MessageService;
import voxera.service.PresenceService;
import voxera.websocket.VoxeraWebSocketHandler;

@Configuration
@EnableWebSocketMessageBroker
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {

    private final PresenceService presenceService;
    private final MessageService messageService;
    private final voxera.repisotory.UserRepository userRepository;

    public WebSocketConfig(PresenceService presenceService, MessageService messageService, voxera.repisotory.UserRepository userRepository) {
        this.presenceService = presenceService;
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new VoxeraWebSocketHandler(presenceService, messageService, userRepository), "/voxera-ws").setAllowedOrigins("*");
    }
}
