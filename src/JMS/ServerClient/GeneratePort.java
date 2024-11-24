package JMS.ServerClient;

import java.util.Random;

public class GeneratePort
{
    static Random random = new Random();
    static int stringToInt;
    public static int randomNum()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <=6; i++)
        {
            int rand = random.nextInt(27);
            stringBuilder.append(rand);
        }
        String string = stringBuilder.toString();
        stringToInt = Integer.parseInt(string);
        return stringToInt;
    }

}
