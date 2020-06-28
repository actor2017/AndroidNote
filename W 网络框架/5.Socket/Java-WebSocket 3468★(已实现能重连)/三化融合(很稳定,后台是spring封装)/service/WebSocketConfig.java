package com.liaoin.microservice.provider.circle.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket配置类
 *
 * @author 张权立
 * @date 2018/7/8 23:47
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(fusionWebSocketHandler(), "/websocket").setAllowedOrigins("*").addInterceptors(handshakeInterceptor());
    }

    @Bean
    public WebSocketHandler fusionWebSocketHandler() {
        return new FusionWebSocketHandler();
    }

    @Bean
    public HandshakeInterceptor handshakeInterceptor() {
        return new FusionHandshakeInterceptor();
    }
}
