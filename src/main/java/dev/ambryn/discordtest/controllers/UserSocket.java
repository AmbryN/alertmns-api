package dev.ambryn.discordtest.controllers;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint(value = "/socket")
public class UserSocket {

    public UserSocket() {}
    @OnOpen
    public void open(Session session) {
        sendAll(session, "Test");
    }
    @OnMessage
    public void message(Session session, String msg) {
        sendAll(session, "test");
    }

    public void sendAll(Session session, String message) {
        for (Session sess : session.getOpenSessions()) {
            try {
                sess.getBasicRemote().sendText("{ \"content\": \""+ message + "\" }");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
