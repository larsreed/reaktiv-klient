package no.mesan.websockets;

import no.mesan.parser.PageParser;
import org.json.JSONObject;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles websocket requests from frontend for pageparser.
 *
 * @author Mikkel Steine
 */
@ServerEndpoint(value = "/pageparser")
public class PageParserEndpoint {
    private static final Map<Session, PageParser> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(final Session session) throws IOException, EncodeException {
        final PageParser client = new PageParser(this, session);
        clients.put(session, client);
    }

    @OnMessage
    public void onMessage(final String message, final Session session) throws IOException {
        final PageParser client = clients.get(session);

        final JSONObject json = new JSONObject(message);
        final String url = json.getString("url");

        client.parseURL(url);
    }

    @OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
        clients.remove(session);
    }

    public boolean sendListOfImages(final Session session, final List<String> imageURLs) {
        if (session == null) { return false; }

        final JSONObject json = new JSONObject();
        json.put("images", imageURLs);

        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (final IOException e) {
            return false;
        }

        return true;
    }
}
