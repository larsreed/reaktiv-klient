package no.mesan.parser;

import no.mesan.websockets.PageParserEndpoint;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles page parser requests and sends results trough websocket (PageParserEndpoint).
 *
 * @author Mikkel Steine
 */
public class PageParser {
    private final PageParserEndpoint endpoint;
    private final Session session;

    public PageParser(final PageParserEndpoint endpoint, final Session session) {
        this.endpoint = endpoint;
        this.session = session;
    }

    public void parseURL(final String url) {
        // TODO: Use URL, parse and send list of image URLs

        // Debug:
        final List<String> images = new ArrayList<>();
        images.add("http://messier45.com/img/halebopp.jpg");
        images.add("http://messier45.com/img/m31.jpg");

        endpoint.sendListOfImages(session, images);
    }
}
