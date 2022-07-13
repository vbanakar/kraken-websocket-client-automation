import com.kraken.interview.Client;
import com.kraken.interview.Utilities;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookNegativeTests
    extends Utilities
{

    private static JSONObject subscriptionErrorResponse;

    private static final String currencyPair = "XBT/USD";
    private static final String bookDepth    = "11";
    private static       String message      = "{\n" +
        "  \"event\": \"subscribe\",\n" +
        "\"pair\": [\n" +
        "    \"" + currencyPair + "\"\n" +
        "  ]," +
        "  \"subscription\": {\n" +
        "  \"depth\": " + bookDepth + ",\n" +
        "    \"name\": \"book\"\n" +
        "  }\n" +
        "}";

    @BeforeAll
    @SneakyThrows
    public static void fetchData ()
    {
        Thread.sleep (5000);
        List<String> messageList = Client.fetchData (message, 60);
        subscriptionErrorResponse = stringToJsonObject (messageList.get (0));

    }

    @Test
    public void depthNotSupportedError ()
    {
        Assertions.assertEquals (subscriptionErrorResponse.getString ("errorMessage"), "Subscription depth not supported");
    }

    @Test
    public void correctCurrencyPairIsReceived ()
    {
        Assertions.assertEquals (currencyPair, subscriptionErrorResponse.getString ("pair"));
    }

    @Test
    public void statusErrorIsReceived ()
    {
        Assertions.assertEquals ("error", subscriptionErrorResponse.getString ("status"));
    }


    @AfterAll
    @SneakyThrows
    public static void waitBeforeNextTest ()
    {
        System.out.println ("***********************");
        System.out.println ("Test Finished");
        System.out.println ("***********************");
        Thread.sleep (10000);
    }

}

