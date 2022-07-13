package com.kraken.interview;

import lombok.Getter;
import lombok.SneakyThrows;

import javax.websocket.*;
import java.util.ArrayList;
import java.util.List;

import static com.kraken.interview.Client.messageLatch;

@ClientEndpoint
public class KrakenEndpoint
{

    @Getter
    private List<String> messageList;
    private String       payload;

    public KrakenEndpoint (String payload)
    {
        this.messageList = new ArrayList<> ();
        this.payload = payload;
    }

    @OnOpen
    @SneakyThrows
    public void onOpen (Session session)
    {
        session.getAsyncRemote ().sendText (payload);
    }

    @OnMessage
    public void processMessage (String message)
    {
        if (message.contains ("connectionID"))
        {
            System.out.println (message);
            System.out.println ("Connection Established");
        }
        else if (!message.contains ("heartbeat"))
        {
            System.out.println (message);
            this.messageList.add (message);
            messageLatch.countDown ();
        }
    }

    @OnError
    public void processError (Throwable t)
    {
        System.out.println (t.getMessage ());
    }

    @OnClose @SneakyThrows
    public void onClose (Session session, CloseReason closeReason)
    {
        System.out.println ("Session " + session.getId () + " close because of " + closeReason);
        messageLatch.countDown ();
    }
}
