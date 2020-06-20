package com.itsq.config;

import com.itsq.webSocket.handler.SocketHandler;
import com.itsq.webSocket.interceptor.WebSocketInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocketConfig
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/3/22
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig/* implements WebSocketConfigurer*/ {
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandler(), "/app")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }

   /* @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }*/

}
