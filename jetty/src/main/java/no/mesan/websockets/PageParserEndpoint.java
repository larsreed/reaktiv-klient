package no.mesan.websockets;

import org.eclipse.jetty.util.ConcurrentHashSet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;

/**
 * TODO
 *
 * @author Mikkel Steine
 */
@ServerEndpoint(value = "/pageparser")
public class PageParserEndpoint {
    private static final Set<Session> clients = new ConcurrentHashSet<>();
    private static String containers;

    @OnOpen
    public void onOpen(final Session session) throws IOException, EncodeException {
        clients.add(session);
        if (containers == null) {
            session.getBasicRemote().sendText("empty");
        } else {
            session.getBasicRemote().sendText(containers);
        }
    }

    @OnMessage
    public String onMessage(final String message, final Session session) throws IOException {
        containers = message;
        synchronized (clients) {
            for (final Session client : clients) {
                client.getBasicRemote().sendText(containers);
            }
        }
        return message;
    }

    @OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
        clients.remove(session);
    }
}
