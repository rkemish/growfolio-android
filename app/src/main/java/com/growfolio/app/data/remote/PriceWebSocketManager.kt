package com.growfolio.app.data.remote

import com.growfolio.app.data.local.AuthDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class WSServerMessage(
    val id: String,
    val type: String, // event, system, ack, error
    val event: String? = null,
    val timestamp: String,
    val data: JsonElement? = null
)

@Serializable
data class WSClientMessage(
    val type: String, // subscribe, unsubscribe, pong, refresh_token
    val channels: List<String>? = null,
    val symbols: List<String>? = null,
    val token: String? = null
)

@Singleton
class PriceWebSocketManager @Inject constructor(
    private val client: OkHttpClient,
    private val authDataStore: AuthDataStore,
    private val json: Json
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var webSocket: WebSocket? = null
    
    private val _events = MutableSharedFlow<WSServerMessage>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<WSServerMessage> = _events.asSharedFlow()

    fun connect() {
        if (webSocket != null) return

        scope.launch {
            val token = authDataStore.authToken.first()
            // Updated URL according to asyncapi.yaml
            val request = Request.Builder()
                .url("wss://api.growfolio.app/api/v1/ws?token=$token")
                .build()

            webSocket = client.newWebSocket(request, object : WebSocketListener() {
                override fun onMessage(webSocket: WebSocket, text: String) {
                    try {
                        val message = json.decodeFromString<WSServerMessage>(text)
                        
                        // Handle heartbeat automatically
                        if (message.event == "heartbeat") {
                            sendPong()
                        }
                        
                        _events.tryEmit(message)
                    } catch (e: Exception) {
                        // Handle parsing error
                    }
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    this@PriceWebSocketManager.webSocket = null
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    this@PriceWebSocketManager.webSocket = null
                }
            })
        }
    }

    private fun sendPong() {
        val pong = WSClientMessage(type = "pong")
        webSocket?.send(json.encodeToString(WSClientMessage.serializer(), pong))
    }

    fun subscribe(channels: List<String>, symbols: List<String>? = null) {
        val message = WSClientMessage(type = "subscribe", channels = channels, symbols = symbols)
        webSocket?.send(json.encodeToString(WSClientMessage.serializer(), message))
    }

    fun unsubscribe(channels: List<String>) {
        val message = WSClientMessage(type = "unsubscribe", channels = channels)
        webSocket?.send(json.encodeToString(WSClientMessage.serializer(), message))
    }

    fun refreshToken(newToken: String) {
        val message = WSClientMessage(type = "refresh_token", token = newToken)
        webSocket?.send(json.encodeToString(WSClientMessage.serializer(), message))
    }

    fun disconnect() {
        webSocket?.close(1000, "User disconnect")
        webSocket = null
    }
}