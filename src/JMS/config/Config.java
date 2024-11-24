package JMS.config;

public class Config
{
    private String encoded;
    private String areaCode;
    private String otherPhoneNumber;
    public Config(String encoded,String otherPhoneNumber, String areaCode)
    {
        this.encoded = encoded;
        this.areaCode = areaCode;
        this.otherPhoneNumber = otherPhoneNumber;
    }

    public String getEncoded()
    {
        return encoded;
    }
    public String getAreaCode()
    {
        return areaCode;
    }
    public String getOtherPhoneNumber(){return otherPhoneNumber;}
}
