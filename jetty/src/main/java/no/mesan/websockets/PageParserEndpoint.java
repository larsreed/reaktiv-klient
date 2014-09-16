package no.mesan.websockets;

import org.json.JSONObject;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles websocket requests from frontend for pageparser.
 *
 * @author Mikkel Steine
 */
@ServerEndpoint(value = "/pageparser")
public class PageParserEndpoint {

    @OnOpen
    public void onOpen(final Session session) throws IOException, EncodeException {
        final JSONObject json = new JSONObject();
        json.put("images", new ArrayList<String>());
        session.getBasicRemote().sendText(json.toString());
    }

    @OnMessage
    public String onMessage(final String message, final Session session) throws IOException {
        final JSONObject json = new JSONObject(message);
        final String url = json.getString("url");

        // TODO: Get URL, parse and return list of URLs

        // Debug:
        final List<String> images = new ArrayList<>();
        images.add("http://messier45.com/img/halebopp.jpg");
        json.put("images", images);

        return json.toString();
    }
}
