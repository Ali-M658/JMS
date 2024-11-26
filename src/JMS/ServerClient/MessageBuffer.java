package JMS.ServerClient;

import java.util.ArrayList;
import java.util.List;

public class MessageBuffer
{
    public static List<String> message = new ArrayList<>();
    public MessageBuffer()
    {
    }

    public static void addMessage(String theMessage)
    {
        message.add(theMessage);
    }

    public String getMessage(int index)
    {
        return message.get(index);
    }

}
