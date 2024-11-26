package JMS;

import JMS.*;

import JMS.SendData.GetAuthToken;
//import JMS.SendData.UploadToCloud;
import JMS.ServerClient.Client;
import JMS.ServerClient.MessageBuffer;
import JMS.ServerClient.Server;
import JMS.UI.UIMessaging;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class TextCenter extends JFrame
{

    private JTextField textField;
    private ArrayList<String> textList = new ArrayList<String>();
    public JTextArea areaToText;
    private JLabel textLabel;
    private UIMessaging messaging = new UIMessaging();
    private Client client;

    String phoneNumber;
    String otherPhoneNumber;
    String areaCode;
    String otherAreaCode;
    String serverAddress;
    private MessageBuffer messageBuffer;
    int serverPort = PortSaver.port;

    public TextCenter(String PH, String OPH, String areaCode, String otherAreaCode, String serverAddress, int serverPort)
    {
        System.out.println("Text center line 40");
        this.phoneNumber = PH;
        this.otherPhoneNumber = OPH;
        this.areaCode = areaCode;
        this.otherAreaCode = otherAreaCode;
        this.client = new Client(serverAddress, serverPort);
        client.connectToServer();
        client.setMessageListener(this::appendRecievedMessage);
        Server server = new Server(phoneNumber, otherPhoneNumber);
        server.trySomething();

        setTitle("Text Area");
        setSize(900,500);
        setLocation(960/2,540/2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        textField = new JTextField();
        add(textField, BorderLayout.NORTH);

        //Text area
        areaToText = new JTextArea();
        areaToText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaToText);
        add(scrollPane,BorderLayout.CENTER);
        //adding scroll pane to same area of text

        textLabel = new JLabel("You are texting "+otherPhoneNumber);
        System.out.println("Buttons were added?");
        add(textLabel,BorderLayout.SOUTH);

        String encoded = StringEncodeDecode.encode(phoneNumber,areaCode);
        textField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String input = textField.getText();
                messageBuffer = new MessageBuffer();
                if (!input.isEmpty())
                {
                    sendTextToLogic(input);
                    areaToText.append("You: "+ input + "\n"); //put input to text area
                    textField.setText(""); //Make it nothing after entered
                    textList.add(input);
                    if (MessageBuffer.message.size()>=0)
                    {
                        areaToText.append("Other: "+ MessageBuffer.message.get(0) + "\n");
                        MessageBuffer.message.remove(0);
                    }
                    client.sendMessage(input);
                }
            }

        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e)
            {
                saveToFile();
            }

        });
    }
    private void appendRecievedMessage(String messageSent)
    {
        SwingUtilities.invokeLater(() -> areaToText.append("Other: " + messageSent + "\n"));
    }
    private void sendTextToLogic(String input)
    {
        client.sendMessage(input);
    }
    private void saveToFile()
    {
        String directory = "C:\\Users\\Public\\";

        File file = null;

        try {

            file = new File(directory+phoneNumber);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
            {
                for (String item : textList)
                {
                    writer.write(item);
                    writer.newLine();
                    System.out.println("written!");
                }
            }

            String cred1 = "0053d08d1f4e8380000000007";
            String cred2 = "K005581aiol3Z4gCFSgTcH9Eau8atsU";
            String auth = GetAuthToken.getAuthToken(cred1,cred2);
            //String[] auth
            String bucketID = "935d50986d115f649e380318";
            //new UploadToCloud(cred1, cred2, bucketID, file, file.getPath());
            System.console().writer().write("Upload to cloud is there");

            if (file.delete())
            {
                System.out.println("File deleted");
            }
            else
            {
                System.out.println("Failed to delete");
            }
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}