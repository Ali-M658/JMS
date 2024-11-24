package JMS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;
import java.util.Properties;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.databind.ObjectMapper;


public class SaveConfig
{
    StringBuilder identification;
    String phonenumber;
    boolean saveOrLoad;
    private ObjectMapper objectMapper;

    public SaveConfig(String phoneNumber, StringBuilder id, boolean saveOrLoad)
    {
        this.phonenumber = phoneNumber;
        this.identification = id;
        this.saveOrLoad = saveOrLoad;
    }
    public void config(boolean saveOrLoad)
    { // IF saveorload is true it will load, if false, it will save
        if (saveOrLoad)
        {
            loadConfig("C:\\Users\\Public\\Public Documents");
        }
        else
        {
            saveConfig("C:\\Users\\Public\\Public Documents");
        }
    }

    private void saveConfig(String path)
    {
        try
        {
            Config config = objectMapper.readValue(new File(path), Config.class);
        }
        catch (IOException e)
        {
            System.err.println("Error loading config: " + e.getMessage());
        }
    }
    private void loadConfig(String path)
    {
        try
        {
            Config config = new Config(identification, phonenumber)
        }
        catch (IOException e)
        {
            System.err.println("Error saving config: "+ e.getMessage());
        }
    }
}
