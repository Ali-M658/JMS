package JMS;


import JMS.TextCenter.TextCenter;

import JMS.UI.UIComponents;
import JMS.config.SaveConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActionHandlers
{
    private File configFile;
    private SaveConfig config;
    private final JFrame frame;
    private final UIComponents components;
    private int nextCounter = 0;
    private int port;

    public List<String> phSaver = new ArrayList<String>();

    public ActionHandlers(JFrame frame, UIComponents components)
    {
        configFile = new File("C:\\Users\\Public\\Public_Documents.json\\");
        this.frame = frame;
        this.components = components;
        this.port = 0;
        System.out.println("The port is: "+ port);
    }


    public void attachActionListeners() {
        components.next.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                nextCounter++;
                System.out.println("next is clicked");
                try {
                    handleNextAction();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        components.back.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                nextCounter--;
                handleBackAction();
            }
        });

        components.loadConfig.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    configCheck(configFile);
                    System.out.println("button clicked");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public String encodeForFile(String phoneNumber,String areaCode)
    {
        return StringEncodeDecode.encode(phoneNumber,areaCode);
    }

    public String decodeForFile(String phoneNumber,String areaCode)
    {
        return StringEncodeDecode.decode(phoneNumber,areaCode);
    }

    private void configCheck(File file) throws IOException {
        if (file.exists())
        {
            ObjectMapper mapper= new ObjectMapper();
            System.out.println("File Exists!");
            JsonNode jsonNode = mapper.readTree(file);
            String otherPhone = jsonNode.get("otherPhoneNumber").asText();
            String areaCode = jsonNode.get("areaCode").asText();
            String encoded = jsonNode.get("encoded").asText();
            String yourPhone = StringEncodeDecode.decode(encoded, areaCode);
            phSaver.add(yourPhone);
            String[] phones = {yourPhone,otherPhone};
            int port = JMS.ServerClient.GeneratePort.getSharedPortForConversation(phones);
            System.out.println("phone number decoded "+ yourPhone);
            String otherAreaCode = components.otherAreaCode.getText();
            SaveConfig config1 = new SaveConfig(encoded,otherPhone, true);
            String ipAddress = "10.0.0.173";
            System.out.println("Port before being passes down: "+ port);
            frame.dispose();
            new TextCenter(yourPhone, otherPhone, areaCode, otherAreaCode, ipAddress, port).setVisible(true);
        }
    }

    private void handleNextAction() throws IOException {
        if (nextCounter == 1)
        {
            toggleVisibility(false, true); //using instance booleanse to toggle it
            //more on responsibility and less function
        }
        else if (nextCounter == 2) //uses elif statements
        {
            System.out.println("NExtcounter is: "+nextCounter);
            String yourPhone = components.yourPhoneNumberField.getText();
            String otherPhone = components.otherPhoneNumberField.getText();
            String areaCode = components.yourAreaCode.getText();
            SaveConfig.areaCodeConstruct(areaCode);
            String otherAreaCode = components.otherAreaCode.getText();
            String encoded = encodeForFile(yourPhone, areaCode);
            String[] phones = {yourPhone,otherPhone};
            int port = JMS.ServerClient.GeneratePort.getSharedPortForConversation(phones);
            String ipAddress = "10.0.0.173";
            SaveConfig configure = new SaveConfig(encoded, otherPhone, false);
            new TextCenter(yourPhone, otherPhone, areaCode, otherAreaCode, ipAddress, port).setVisible(true);
            //Saves it
            frame.dispose();
        }
    }

    private void handleBackAction() {
        if (nextCounter == 0) {
            toggleVisibility(true, false);
        }
    }

    private void toggleVisibility(boolean isFirstStep, boolean isSecondStep)
    {
        components.yourPhoneNumberLabel.setVisible(isFirstStep);
        components.yourPhoneNumberField.setVisible(isFirstStep);
        components.yourAreaCodeLabel.setVisible(isFirstStep);
        components.yourAreaCode.setVisible(isFirstStep);
        //good work
        components.otherPhoneNumberLabel.setVisible(isSecondStep);
        components.otherPhoneNumberField.setVisible(isSecondStep);
        components.otherAreaCodeLabel.setVisible(isSecondStep);
        components.otherAreaCode.setVisible(isSecondStep);
    }
}
