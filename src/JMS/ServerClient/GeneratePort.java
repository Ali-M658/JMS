package JMS.ServerClient;

import java.util.Random;

public class GeneratePort
{
    static Random random = new Random();
    static int stringToInt;
    public static int randomNum()
    {
        stringToInt = random.nextInt(1,65535);
        return stringToInt;
    }

}
