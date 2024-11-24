package JMS.config;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;

import JMS.UI.UIComponents;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SaveConfig
{
    String identification;
    String encoded;
    static String areaCode;
    String otherPhone;

    boolean saveOrLoad;
private ObjectMapper objectMapper = new ObjectMapper();

    public SaveConfig(String encoded, String otherPhoneNumber, boolean saveOrLoad)
    {
        this.encoded = encoded;
        this.saveOrLoad = saveOrLoad;
        this.otherPhone = otherPhoneNumber;
        config(saveOrLoad);
    }
    public static void areaCodeConstruct(String areaCodee)
    {
        areaCode = areaCodee;
    }
    public void config(boolean saveOrLoad)
    { // IF saveorload is true it will load, if false, it will save
        if (saveOrLoad)
        {
            loadConfig("C:\\Users\\Public\\Public_Documents.json\\");
        }
        else
        {
            saveConfig("C:\\Users\\Public\\Public_Documents.json\\");
        }
    }

    public void loadConfig(String path)
    {
        try
        {
            Config config = objectMapper.readValue(new File(path), Config.class);
        }
        catch (IOException e)
        {
            System.err.println("Error loading config: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void saveConfig(String path)
    {
        try
        {
            System.out.println("SaveConfig was executed!");
            JMS.config.Config config = new JMS.config.Config(encoded, otherPhone, areaCode);
            System.out.println("Config stuff: "+ config.getEncoded());
            System.out.println("Area Code stuff: "+ config.getAreaCode());
            System.out.println("OtherPhoneNumber stuff: "+ config.getOtherPhoneNumber());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), config);
        }
        catch (IOException e)
        {
            System.err.println("Error saving config: "+ e.getMessage());
        }
    }
}

