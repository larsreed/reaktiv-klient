package no.mesan.websockets;

import no.mesan.parser.PageParser;
import org.json.JSONObject;

import javax.websocket.*;
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
        final JSONObject json = new JSONObject(message);
        final String action = json.getString("action");
        if (action == null) {
            return;
        }

        final PageParser client = clients.get(session);
        if (action.equals("stop")) {
            client.stop();
        } else if (action.equals("parse")) {
            final String url = json.getString("url");
            client.parseURL(url);
        }
    }

    @OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
        clients.remove(session);
    }

    public boolean sendListOfImages(final Session session, final List<String> imageURLs) {
        if (session == null) { return false; }

        final JSONObject json = prepareMessageObject("images");
        json.put("images", imageURLs);

        return sendMessage(session, json);
    }

    public boolean sendStopped(final Session session) {
        return session != null && sendMessage(session, prepareMessageObject("stopped"));
    }

    private JSONObject prepareMessageObject(final String type) {
        final JSONObject json = new JSONObject();
        json.put("type", type);
        return json;
    }

    private boolean sendMessage(final Session session, final JSONObject json) {
        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (final IOException e) {
            return false;
        }

        return true;
    }
}
