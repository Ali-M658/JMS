package JMS;



import java.util.ArrayList;
/** Notes: You have to specify instance and things very specifically
 * */
public class TextLogic
{
    String textMessages;
    String senderPhoneNumber;
    String yourPhoneNumber;
    public TextLogic(String textMessage, String PH, String YPH)
    {
        this.textMessages = textMessage;
        this.senderPhoneNumber = PH;
        this.yourPhoneNumber = YPH;
    }
    private String oneMessage(String textMessage, int index)
    {
        return textMessage;
    }
    //private void initializeTwilio
    //{

    //}


}
