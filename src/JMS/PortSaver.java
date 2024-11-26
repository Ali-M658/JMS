package JMS;

import JMS.UI.UIMessaging;

public class PortSaver
{
    static int port;
    public PortSaver(int port)
    {
        this.port = port;
    }
    public static int getPort()
    {
        return port;
    }
}
