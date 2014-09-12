package no.mesan.server;

import no.mesan.websockets.PageParserEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

/**
 *
 */
public class WebappStarter {
    public static void main(final String[] args) throws Exception {
        final WebAppContext context = new WebAppContext();

        context.setContextPath("/");

        // Development setup
        final ServletHolder disableStaticFileLock = new ServletHolder(new DefaultServlet());
        disableStaticFileLock.setInitParameter("useFileMappedBuffer", "false");
        context.addServlet(disableStaticFileLock, "/");

        // Runnable jar setup
        context.setResourceBase(ClassLoader.getSystemResource("public").toExternalForm());

        final Server server = new Server(8080);
        server.setHandler(context);

        final ServerContainer webSocketContainer = WebSocketServerContainerInitializer.configureContext(context);
        webSocketContainer.addEndpoint(PageParserEndpoint.class);

        server.start();
        server.join();
    }
}
