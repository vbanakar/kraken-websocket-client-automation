import com.kraken.interview.Client;
import com.kraken.interview.Utilities;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TickerTests
    extends Utilities
{

    private static JSONObject   subscriptionDetails;
    private static Integer      channelId;
    private static String       subscriptionName;
    private static List<String> dataRecords;
    private static JSONArray    firstDataRecord;

    private static final String currencyPair = "XBT/USD";

    private static String message = "{\n" +
        "  \"event\": \"subscribe\",\n" +
        "\"pair\": [\n" +
        "    \"" + currencyPair + "\"\n" +
        "  ]," +
        "  \"subscription\": {\n" +
        "    \"name\": \"ticker\"\n" +
        "  }\n" +
        "}";

    @BeforeAll @SneakyThrows
    public static void fetchData ()
    {
        List<String> messageList = Client.fetchData (message, 30);
        subscriptionDetails = stringToJsonObject (messageList.get (0));
        channelId = (Integer)subscriptionDetails.get ("channelID");
        subscriptionName = ((JSONObject)subscriptionDetails.get ("subscription")).get ("name").toString ();
        dataRecords = filterList (messageList);
        firstDataRecord = stringToJsonArray (dataRecords.get (0));
    }
    
    

    @Test
    public void channelIdNotZero ()
    {
        Assertions.assertTrue (channelId != 0, "c is not received");
    }

    @Test
    public void subscriptionNameIsCorrect ()
    {
        Assertions.assertEquals ("ticker", subscriptionName);
    }

    @Test
    public void correctCurrencyPairIsReceived ()
    {
        Assertions.assertEquals (currencyPair, subscriptionDetails.get ("pair").toString ());
    }

    @Test
    public void dataRecordCurrencyPairIsCorrect ()
    {
        Assertions.assertEquals (currencyPair, firstDataRecord.getString (3));
    }

    @Test
    public void dataRecordSubscriptionIDIsCorrect ()
    {
        Assertions.assertEquals ("ticker", firstDataRecord.getString (2));
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
