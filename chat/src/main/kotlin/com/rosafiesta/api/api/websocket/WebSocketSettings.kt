package com.rosafiesta.api.api.websocket

import com.rosafiesta.api.infra.configs.WebSocketConfig
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketSettings(
  private val handler: ChatWebSocketHandler,
  private val webSocketConfig: WebSocketConfig
): WebSocketConfigurer {
  override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
     registry.addHandler(handler, "/ws/chat")
       .setAllowedOrigins(
          *webSocketConfig.allowedOrigins.toTypedArray()
       )
  }
}