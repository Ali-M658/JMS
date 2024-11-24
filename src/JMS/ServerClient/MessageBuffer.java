package JMS.ServerClient;

import java.util.ArrayList;
import java.util.List;

public class MessageBuffer
{
    List<String> message = new ArrayList<>();
    public MessageBuffer(String message)
    {
        this.message.add(message);
    }
    public String getMessage(int index)
    {
        return message.get(index);
    }

}
