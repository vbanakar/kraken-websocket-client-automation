package com.kraken.interview;

import lombok.SneakyThrows;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Client
{
    final static CountDownLatch messageLatch = new CountDownLatch (10);

    @SneakyThrows
    public static List<String> fetchData (String payload, int timeout)
    {

        WebSocketContainer container = ContainerProvider.getWebSocketContainer ();
        String             uri       = getWebSocketURI ();
        System.out.println ("Connecting to " + uri);
        KrakenEndpoint krakenEndpoint = new KrakenEndpoint (payload);
        Session        session        = container.connectToServer (krakenEndpoint, URI.create (uri));
        messageLatch.await (timeout, TimeUnit.SECONDS);
        if (session.isOpen ()) {
            session.close ();
        }
        return krakenEndpoint.getMessageList ();

    }

    private static String getWebSocketURI ()
    {
        return ResourceBundle.getBundle ("config").getString ("web_socket_uri");
    }
}
