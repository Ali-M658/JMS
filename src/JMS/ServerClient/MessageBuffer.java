package JMS.ServerClient;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MessageBuffer
{
    public static List<String> message = new LinkedList<>();

    public MessageBuffer()
    {
    }

    public void addMessage(String theMessage)
    {
        message.add(theMessage);
    }

    public String getMessage(int index)
    {
        return message.get(index);
    }

}
