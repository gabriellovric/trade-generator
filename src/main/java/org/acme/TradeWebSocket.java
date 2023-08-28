package org.acme;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/trade-websocket/{clientId}")
@ApplicationScoped
public class TradeWebSocket {

    private Map<String, Session> sessions = new ConcurrentHashMap<>();
    private AtomicInteger totalOrders = new AtomicInteger();

    @OnOpen
    public void onOpen(Session session, @PathParam("clientId") String clientId) {
        sessions.put(clientId, session);
        System.out.println("onOpen> " + clientId);

    }

    @OnClose
    public void onClose(Session session, @PathParam("clientId") String clientId) {
        sessions.remove(clientId);
        System.out.println("onClose> " + clientId);
    }

    @OnError
    public void onError(Session session, @PathParam("clientId") String clientId, Throwable throwable) {
        sessions.remove(clientId);
        System.out.println("onError> " + clientId);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("clientId") String clientId) {
        System.out.println("onMessage> " + clientId + ": " + message);
    }

    @Scheduled(every = "1s")
    void sendTrade() {
        if (sessions != null) {
            Trade trade = TradeGenerator.generate();

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                String json = mapper.writeValueAsString(trade);
                broadcast(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            totalOrders.incrementAndGet();
        }
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result -> {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
